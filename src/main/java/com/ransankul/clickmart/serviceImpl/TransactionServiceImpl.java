package com.ransankul.clickmart.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;

import com.ransankul.clickmart.model.Transaction;
import com.ransankul.clickmart.repositery.TransactionRepositery;
import com.ransankul.clickmart.repositery.UserRepositery;
import com.ransankul.clickmart.service.TransactionService;
import com.razorpay.Order;

@Service
public class TransactionServiceImpl implements TransactionService{
	
    @Autowired
    private TransactionRepositery transactionRepositery;

	@Override
	public void saveTransaction(Transaction tra) {
		transactionRepositery.save(tra);
	}


}
