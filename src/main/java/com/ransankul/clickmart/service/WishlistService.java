package com.ransankul.clickmart.service;

import java.util.List;

import com.ransankul.clickmart.model.Wishlist;

public interface WishlistService {
	
	public Wishlist addProductToWishlist(Wishlist wishlist);

	public void removeProductToWishlist(int userId,int productId);
	
	public Wishlist getById(int id);
	
	public List<Wishlist> getAllWishlistProduct(int userId,String pageNumber);
	
	public boolean isProductintoWishList(int userId,int productId);
	
	public Wishlist getWishListId(int userId,int productId);

}
