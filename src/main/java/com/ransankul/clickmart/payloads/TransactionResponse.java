package com.ransankul.clickmart.payloads;

import java.util.Date;

public class TransactionResponse {
	
	private String imageName;
	private String productId;
	private String productName;
	private Date orderTime;
	private String paymentStatus;
	private String transactionId;
	
	public TransactionResponse(){}
	
	public TransactionResponse(String imageName, String productId, String productName, Date orderTime,
			String paymentStatus, String transactionId) {
		super();
		this.imageName = imageName;
		this.productId = productId;
		this.productName = productName;
		this.orderTime = orderTime;
		this.paymentStatus = paymentStatus;
		this.transactionId = transactionId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
