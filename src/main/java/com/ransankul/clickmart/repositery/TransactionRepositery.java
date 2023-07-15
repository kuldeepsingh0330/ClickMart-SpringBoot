package com.ransankul.clickmart.repositery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ransankul.clickmart.model.Transaction;
import com.razorpay.Order;

public interface TransactionRepositery extends JpaRepository<Transaction, Long>{

}
