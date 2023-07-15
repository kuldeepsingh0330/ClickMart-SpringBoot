package com.ransankul.clickmart.service;

import com.ransankul.clickmart.model.Transaction;
import com.razorpay.Order;

public interface TransactionService {

	public void saveTransaction(Transaction order);
	
}
