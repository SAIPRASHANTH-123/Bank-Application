package com.bank.model;

import java.sql.Date;
import java.time.LocalDate;

public class Customerlogin {
    private int accountNo;
    private String fullName;
    private String address;
    private String mobileNo;
    private String emailId;
    private String accountType;
    private double initialBalance;
    private LocalDate dateOfBirth;
    private String idProof;
    private String temporaryPassword;
    private String password;
    private boolean firstLogin;

    public Customerlogin() {}

    public Customerlogin(String fullName, String address, String mobileNo, String emailId, String accountType, double initialBalance, String dateOfBirth, String idProof, String temporaryPassword) {
        this.fullName = fullName;
        this.address = address;
        this.mobileNo = mobileNo;
        this.emailId = emailId;
        this.accountType = accountType;
        this.initialBalance = initialBalance;
        this.dateOfBirth = LocalDate.parse(dateOfBirth);
        this.idProof = idProof;
        this.temporaryPassword = temporaryPassword;
        this.firstLogin = firstLogin;
    }
    
	public Customerlogin(String fullName, String address, String mobileNo, String emailId, String accountType,
			LocalDate dateOfBirth, String idProof) {
		super();
		this.fullName = fullName;
		this.address = address;
		this.mobileNo = mobileNo;
		this.emailId = emailId;
		this.accountType = accountType;
		this.dateOfBirth = dateOfBirth;
		this.idProof = idProof;
	}

	public Customerlogin(int accountNo, String fullName, String address, String mobileNo, String emailId, String accountType,String idProof) {
		super();
		this.accountNo = accountNo;
		this.fullName = fullName;
		this.address = address;
		this.mobileNo = mobileNo;
		this.emailId = emailId;
		this.accountType = accountType;
		this.idProof = idProof;
	}

	public Customerlogin(String fullName, String address, String mobileNo, String emailId, String accountType,
			String idProof) {
		this.fullName = fullName;
		this.address = address;
		this.mobileNo = mobileNo;
		this.emailId = emailId;
		this.accountType = accountType;
		this.idProof = idProof;
	}

	public int getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public double getInitialBalance() {
		return initialBalance;
	}

	public void setInitialBalance(double initialBalance) {
		this.initialBalance = initialBalance;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getIdProof() {
		return idProof;
	}

	public void setIdProof(String idProof) {
		this.idProof = idProof;
	}

	public String getTemporaryPassword() {
		return temporaryPassword;
	}

	public void setTemporaryPassword(String temporaryPassword) {
		this.temporaryPassword = temporaryPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

}	

