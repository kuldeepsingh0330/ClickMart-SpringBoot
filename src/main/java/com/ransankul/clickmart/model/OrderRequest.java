package com.ransankul.clickmart.model;

import java.util.List;

public class OrderRequest {
	
    private List<Integer> productList;
    private List<Integer> quantityList;
    private Address deliveryAddress;
	
	
	public OrderRequest() {
		super();
	}


	public OrderRequest(List<Integer> productList, List<Integer> quantityList, Address deliveryAddress) {
		super();
		this.productList = productList;
		this.quantityList = quantityList;
		this.deliveryAddress = deliveryAddress;
	}


	public List<Integer> getProductList() {
		return productList;
	}


	public void setProductList(List<Integer> productList) {
		this.productList = productList;
	}


	public List<Integer> getQuantityList() {
		return quantityList;
	}


	public void setQuantityList(List<Integer> quantityList) {
		this.quantityList = quantityList;
	}


	public Address getDeliveryAddress() {
		return deliveryAddress;
	}


	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}


	@Override
	public String toString() {
		return "OrderRequest [productList=" + productList + ", quantityList=" + quantityList + ", deliveryAddress="
				+ deliveryAddress + "]";
	}
	
	

}
