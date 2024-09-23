package com.bank.model;

import java.sql.Timestamp;

public class Transaction {
	private int TransactionId;
	private int AccountNo;
	private String TransactionType;
	private double Amount;
	private Timestamp TransactionDate;
	
	public Transaction(int TransactionId, int AccountNo, String TransactionType, double Amount, Timestamp TransactionDate) {
			this.TransactionId = TransactionId;
			this.AccountNo = AccountNo;
			this.TransactionType = TransactionType;
			this.Amount = Amount;
			this.TransactionDate = TransactionDate;
	}

	public int getTransactionId() {
		return TransactionId;
	}

	public void setTransactionId(int transactionId) {
		TransactionId = transactionId;
	}

	public int getAccountNo() {
		return AccountNo;
	}

	public void setAccountNo(int accountNo) {
		AccountNo = accountNo;
	}

	public String getTransactionType() {
		return TransactionType;
	}

	public void setTransactionType(String transactionType) {
		TransactionType = transactionType;
	}

	public double getAmount() {
		return Amount;
	}

	public void setAmount(double amount) {
		Amount = amount;
	}

	public Timestamp getTransactionDate() {
		return TransactionDate;
	}

	public void setTransactionDate(Timestamp transactionDate) {
		TransactionDate = transactionDate;
	}
	

}
