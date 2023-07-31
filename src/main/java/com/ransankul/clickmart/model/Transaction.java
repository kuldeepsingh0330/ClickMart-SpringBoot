package com.ransankul.clickmart.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private int addressId;
    private String paymentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ordered_product",
    joinColumns=@JoinColumn(name="transaction",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name="product",referencedColumnName = "product_id")
    )
    private List<Product> orderedProduct = new ArrayList<>();
    
    
    
    
    public String getOrder_id() {
		return order_id;
	}



	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}



	public String getPaymentId() {
		return paymentId;
	}



	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

    public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public List<Product> getOrderedProduct() {
		return orderedProduct;
	}



	public void setOrderedProduct(List<Product> orderedProduct) {
		this.orderedProduct = orderedProduct;
	}



	// Default constructor (required by Hibernate)
    public Transaction() {
    }
    
    

    public Transaction(int amount, int amount_paid, String notes, Date created_at, int amount_due,
			String currency, String receipt, String order_id, String entity, String offer_id, String status, int attempts) {
		super();
		this.amount = amount;
		this.amount_paid = amount_paid;
		this.notes = notes;
		this.created_at = created_at;
		this.amount_due = amount_due;
		this.currency = currency;
		this.receipt = receipt;
		this.entity = entity;
		this.order_id = order_id;
		this.offer_id = offer_id;
		this.status = status;
		this.attempts = attempts;
	}



	// Getters and setters

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



	public int getAddress() {
		return addressId;
	}



	public void setAddress(int address) {
		this.addressId = address;
	}   
	
    
}

