package com.ransankul.clickmart.repositery;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ransankul.clickmart.model.Transaction;
import com.ransankul.clickmart.model.User;
import com.razorpay.Order;

public interface TransactionRepositery extends JpaRepository<Transaction, Long>{
	

	@Query("SELECT tr FROM Transaction tr where tr.order_id = :order_id")
	Transaction findByOrder_id(String order_id);
	

	@Query("SELECT tr FROM Transaction tr where tr.user = :user ORDER BY tr.order_id DESC")
	List<Transaction> findByUser(User user,Pageable p);

}
