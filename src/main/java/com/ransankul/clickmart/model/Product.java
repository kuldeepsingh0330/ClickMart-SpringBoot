package com.ransankul.clickmart.model;

import java.util.ArrayList;

public class Product {

    private String name;
    private double price, discount;
    private int quantity, id;
    private boolean isAvailable;
    private ArrayList<String> images;
    private int category; 



    public Product(String name, double price, double discount, int quantity, int id, boolean isAvailable,
            ArrayList<String> images, int category) {
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
        this.id = id;
        this.isAvailable = isAvailable;
        this.images = images;
        this.category = category;
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
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    public ArrayList<String> getImages() {
        return images;
    }
    public void setImages(ArrayList<String> images) {
        this.images = images;
    }


    public int getCategory() {
        return category;
    }


    public void setCategory(int category) {
        this.category = category;
    }

    
    
    
    
}
