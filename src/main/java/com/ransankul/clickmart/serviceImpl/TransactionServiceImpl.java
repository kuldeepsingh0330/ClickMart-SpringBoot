package com.ransankul.clickmart.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;

import com.ransankul.clickmart.model.Transaction;
import com.ransankul.clickmart.model.User;
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

	@Override
	public Transaction getTransactionByTransactionId(String id) {
		return transactionRepositery.findByOrder_id(id);
	}

	@Override
	public List<Transaction> getAllTransactionByUser(User user, String pageNumber) {
		Pageable p = PageRequest.of(Integer.parseInt(pageNumber), 20);
		return transactionRepositery.findByUser(user,p);
	}


}
