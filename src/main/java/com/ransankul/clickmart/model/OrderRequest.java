package com.ransankul.clickmart.model;

import java.util.List;

import com.ransankul.clickmart.payloads.AddressResponse;

public class OrderRequest {
	
    private List<Integer> productList;
    private List<Integer> quantityList;
    private AddressResponse deliveryAddress;
	
	
	public OrderRequest() {
		super();
	}


	public OrderRequest(List<Integer> productList, List<Integer> quantityList, AddressResponse deliveryAddress) {
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


	public AddressResponse getDeliveryAddress() {
		return deliveryAddress;
	}


	public void setDeliveryAddress(AddressResponse deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}


	@Override
	public String toString() {
		return "OrderRequest [productList=" + productList + ", quantityList=" + quantityList + ", deliveryAddress="
				+ deliveryAddress + "]";
	}
	
	

}
