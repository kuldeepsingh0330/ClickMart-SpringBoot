package com.ransankul.clickmart.payloads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ransankul.clickmart.model.Address;
import com.ransankul.clickmart.model.Product;

public class TransactionAdminResponse {

    private Long id;
    private int amount;
    private int amount_paid;
    private String notes;
    private Date created_at;
    private int amount_due;
    private String currency;
    private String receipt;
    private String entity;
    private String order_id;
    private String offer_id;
    private String status;
    private int attempts;
    private String paymentId;
    private int user_id;
    private String user_phoneNumber;
    private String user_name;
    private String user_emailId;
    private String username;
    
    private List<Product> orderedProduct = new ArrayList<>();

    
    

    public TransactionAdminResponse() {
    }


    public int getUser_id() {
        return user_id;
    }


    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public String getUser_phoneNumber() {
        return user_phoneNumber;
    }


    public void setUser_phoneNumber(String user_phoneNumber) {
        this.user_phoneNumber = user_phoneNumber;
    }


    public String getUser_name() {
        return user_name;
    }


    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    public String getUser_emailId() {
        return user_emailId;
    }


    public void setUser_emailId(String user_emailId) {
        this.user_emailId = user_emailId;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(int amount_paid) {
        this.amount_paid = amount_paid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public int getAmount_due() {
        return amount_due;
    }

    public void setAmount_due(int amount_due) {
        this.amount_due = amount_due;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public List<Product> getOrderedProduct() {
        return orderedProduct;
    }

    public void setOrderedProduct(List<Product> orderedProduct) {
        this.orderedProduct = orderedProduct;
    }

    
    
    
}
