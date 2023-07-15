package com.ransankul.clickmart.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @NotBlank(message = "Name is required")
    @Column(name = "name")
    private String name;
    
    @NotBlank(message = "Description is required")
    @Column(name = "description")
    private String description;

    @PositiveOrZero(message = "Price must be a positive or zero value")
    @Column(name = "price")
    private double price;

    @PositiveOrZero(message = "Discount must be a positive or zero value")
    @Max(value = 100, message = "Discount cannot exceed 100")
    @Column(name = "discount")
    private double discount;

    @PositiveOrZero(message = "Quantity must be a positive or zero value")
    @Column(name = "quantity")
    private int quantity;

    @NotNull(message = "Availability flag is required")
    @Column(name = "is_available")
    private boolean isAvailable;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @NotEmpty(message = "At least one image is required")
    @Column(name = "image")
    private List<String> images;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Product() {
        // Default constructor required by Hibernate
    }


    public Product(int productId, String name, double price, double discount, int quantity, boolean isAvailable,
            ArrayList<String> images, Category category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
        this.isAvailable = isAvailable;
        this.images = images;
        this.category = category;
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getDiscount() {
        return discount;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    public List<String> getImages() {
        return images;
    }
    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
    


    public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public void setImages(List<String> images) {
		this.images = images;
	}


	public Category getCategory() {
        return category;
    }


    public void setCategory(Category category) {
        this.category = category;
    }
       
}
