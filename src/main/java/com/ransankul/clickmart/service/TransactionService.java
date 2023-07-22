package com.ransankul.clickmart.service;

import java.util.List;

import com.ransankul.clickmart.model.Transaction;
import com.ransankul.clickmart.model.User;
import com.razorpay.Order;

public interface TransactionService {

	public void saveTransaction(Transaction order);
	public Transaction getTransactionByTransactionId(String id);
	public List<Transaction> getAllTransactionByUser(User user,String pageNumber);
	
}
