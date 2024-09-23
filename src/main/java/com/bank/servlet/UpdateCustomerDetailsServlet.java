package com.bank.servlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.dao.AdminDAO;

@WebServlet("/UpdateCustomerDetailsServlet")
public class UpdateCustomerDetailsServlet extends HttpServlet {
	private static final String UPDATE_CUSTOMER_SQL = "UPDATE Customer SET full_name= ?, address = ?, mobile_no = ?, email_id = ?, date_of_birth = ? WHERE account_no = ?";
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int account_no = Integer.parseInt(request.getParameter("accountno"));
		String full_name = request.getParameter("full_name");
		String address = request.getParameter("address");
		String mobile_no = request.getParameter("mobile_no");
		String date_of_birth = request.getParameter("date_of_birth");
		String email_id = request.getParameter("email_id");
		
		
		AdminDAO admin = new AdminDAO();
		try(Connection connection = admin.getConnection();
				PreparedStatement UpdateCustomerStmt = connection.prepareStatement(UPDATE_CUSTOMER_SQL)){
			
			UpdateCustomerStmt.setString(1, full_name);
			UpdateCustomerStmt.setString(2, address);
			UpdateCustomerStmt.setString(3, mobile_no);
			UpdateCustomerStmt.setString(4, email_id);
			UpdateCustomerStmt.setDate(5, java.sql.Date.valueOf(date_of_birth));
			UpdateCustomerStmt.setInt(6, account_no);
			
			
			
			
			int Updated =  UpdateCustomerStmt.executeUpdate();
			
			if(Updated > 0) {
				response.getWriter().println("Customer Details Updated Successfully");
			}
			else {
				response.getWriter().println("Customer Details Updation Failed");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

}
