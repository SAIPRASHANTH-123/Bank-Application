package com.bank.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.bank.model.Customerlogin;
import com.bank.model.Transaction;

public class CustomerDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/bankdb?allowPublicKeyRetrieval=true&useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Jeeva@2003";

    private static final String INSERT_CUSTOMER_SQL = "INSERT INTO Customer (full_name, address, mobile_no, email_id, account_type, initial_balance, date_of_birth, id_proof, temporary_password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_CUSTOMER_BY_ACCOUNT_NO = "SELECT * FROM Customer WHERE account_no = ? AND temporary_password = ?";
    private static final String UPDATE_CUSTOMER_PASSWORD_SQL = "UPDATE Customer SET password = ? WHERE account_no = ? AND temporary_password = ?";
    private static final String AUTHENTICATE_CUSTOMER_SQL = "SELECT * FROM Customer WHERE account_no = ? AND (password = ? OR temporary_password = ?)";
    
    private static final String UPDATE_BALANCE_SQL = "UPDATE Customer SET initial_balance = initial_balance + ? WHERE account_no = ?";
    private static final String INSERT_TRANSACTION_SQL = "INSERT INTO Transaction (account_no, transaction_type, amount) VALUES (?, ?, ?)";
    private static final String CHECK_BALANCE_SQL = "SELECT initial_balance FROM Customer WHERE account_no = ?";
    
    private static final String SELECT_BALANCE_SQL = "SELECT initial_balance FROM Customer WHERE account_no = ?";
    private static final String SELECT_TRANSACTION_SQL = "SELECT * FROM Transaction WHERE account_no = ? ORDER BY transaction_date DESC LIMIT 10";
    
    private static final String FIRST_LOGIN_SQL = "SELECT first_login FROM Customer WHERE account_no = ?";
    private static final String UPDATE_LOGIN_STATUS_SQL = "UPDATE Customer SET first_login = 0 WHERE account_no = ?";
 

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public int registerCustomer(Customerlogin customer) {
        int accountNo = -1;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CUSTOMER_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, customer.getFullName());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setString(3, customer.getMobileNo());
            preparedStatement.setString(4, customer.getEmailId());
            preparedStatement.setString(5, customer.getAccountType());
            preparedStatement.setDouble(6, customer.getInitialBalance());
            preparedStatement.setDate(7, java.sql.Date.valueOf(customer.getDateOfBirth()));
            preparedStatement.setString(8, customer.getIdProof());
            preparedStatement.setString(9, customer.getTemporaryPassword());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        accountNo = generatedKeys.getInt(1);	
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountNo;
    }

    public boolean setupNewPassword(int accountNo, String tempPassword, String newPassword) {
        boolean success = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CUSTOMER_PASSWORD_SQL)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, accountNo);
            preparedStatement.setString(3, tempPassword);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public Customerlogin authenticateCustomer(int accountNo, String password) {
    	Customerlogin customer = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AUTHENTICATE_CUSTOMER_SQL)) {
            preparedStatement.setInt(1, accountNo);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, password);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                customer = new Customerlogin();
                customer.setAccountNo(rs.getInt("account_no"));
                customer.setFullName(rs.getString("full_name"));
                customer.setAddress(rs.getString("address"));
                customer.setMobileNo(rs.getString("mobile_no"));
                customer.setEmailId(rs.getString("email_id"));
                customer.setAccountType(rs.getString("account_type"));
                customer.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                customer.setIdProof(rs.getString("id_proof"));
                customer.setInitialBalance(rs.getDouble("initial_balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
    public boolean deposit(int accountNo, double amount) {
        boolean success = false;
        try (Connection connection = getConnection();
             PreparedStatement updateBalanceStmt = connection.prepareStatement(UPDATE_BALANCE_SQL);
             PreparedStatement insertTransactionStmt = connection.prepareStatement(INSERT_TRANSACTION_SQL)) {

            connection.setAutoCommit(false);

            updateBalanceStmt.setDouble(1, amount);
            updateBalanceStmt.setInt(2, accountNo);
            int affectedRows = updateBalanceStmt.executeUpdate();

            if (affectedRows > 0) {
                insertTransactionStmt.setInt(1, accountNo);
                insertTransactionStmt.setString(2, "DEPOSIT");
                insertTransactionStmt.setDouble(3, amount);
                insertTransactionStmt.executeUpdate();

                connection.commit();
                success = true;
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
    
    public boolean withdraw(int accountNo,double amount) {
    	boolean success = false;
    	
    	try (Connection connection = getConnection();
    			PreparedStatement checkBalanceStmt = connection.prepareStatement(CHECK_BALANCE_SQL);
    			PreparedStatement updateBalanceStmt = connection.prepareStatement(UPDATE_BALANCE_SQL);
    			PreparedStatement insertTransactionStmt = connection.prepareStatement(INSERT_TRANSACTION_SQL)){
    		
    			checkBalanceStmt.setInt(1,accountNo);
    			ResultSet rs = checkBalanceStmt.executeQuery();
    			
    			if(rs.next()) {
    				double balance = rs.getDouble("initial_balance");
    				
    				if (balance >= amount) {
    					connection.setAutoCommit(false);
    					
    					updateBalanceStmt.setDouble(1,-amount);
    					updateBalanceStmt.setInt( 2, accountNo);
    					
    					int affectedRows = updateBalanceStmt.executeUpdate();
    					
    					if(affectedRows>0) {
    						insertTransactionStmt.setInt(1,accountNo);
    						insertTransactionStmt.setString(2,"WITHDRAW");
                            insertTransactionStmt.setDouble(3, amount);
                            insertTransactionStmt.executeUpdate();

                            connection.commit();
                            success = true;
    					} else {
    						connection.rollback();
    					}
    					
    				}
    			}
    		
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return success;
    }
    
    public double balance(int accountno) {
    	double balance = 0.0;
    	try(Connection connection = getConnection();
    			PreparedStatement balanceStmt = connection.prepareStatement(SELECT_BALANCE_SQL)){
    		
    		balanceStmt.setInt(1,accountno);
    		ResultSet rs= balanceStmt.executeQuery();
    		
    		if(rs.next()) {
    			balance = rs.getDouble("initial_balance");
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return balance;
    }
    
    public List<Transaction> getTransactions(int accountNo){
    	List<Transaction> transactions = new ArrayList<>();
    	try(Connection connection = getConnection();
    			PreparedStatement transactionStmt = connection.prepareStatement(SELECT_TRANSACTION_SQL)){
    		transactionStmt.setInt(1,accountNo);
    		
    		ResultSet rs = transactionStmt.executeQuery();
    		while(rs.next()) {
    			int TransactionId = rs.getInt("transaction_id");
    			String TransactionType = rs.getString("transaction_type");
    			double Amount = rs.getDouble("amount");
    			Timestamp TransactionDate = rs.getTimestamp("transaction_date");
    			
    			Transaction transaction = new Transaction(TransactionId, accountNo, TransactionType, Amount, TransactionDate);
                transactions.add(transaction);
    			
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return transactions;
    }
    
    public boolean checkfirstLogin(int accountNo) {
    	int firstLogin;
    	try(Connection connection = getConnection();
    			PreparedStatement firstLoginStmt = connection.prepareStatement(FIRST_LOGIN_SQL)){
    		firstLoginStmt.setInt(1, accountNo);
    		ResultSet rs = firstLoginStmt.executeQuery();
    		
    		if(rs.next()) {
    			firstLogin = rs.getInt("first_login");
    			
    			if(firstLogin == 1) {
    				return true;
    			}
    			else {
    				return false;
    			}
    		}
    		
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
		return true;

    }
    
    public int changeLoginStatus(int accountNo) {
    	try(Connection connection = getConnection();
    			PreparedStatement changeLoginStmt = connection.prepareStatement(UPDATE_LOGIN_STATUS_SQL)){
    		connection.setAutoCommit(false);
    		
    		changeLoginStmt.setInt(1, accountNo);
    		changeLoginStmt.executeUpdate();
    		
    		connection.commit();
    		
    	}	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return 1;
    }
}
