package com.ransankul.clickmart.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ransankul.clickmart.model.Product;
import com.ransankul.clickmart.model.User;
import com.ransankul.clickmart.model.Wishlist;
import com.ransankul.clickmart.repositery.WishlistRepositery;
import com.ransankul.clickmart.service.WishlistService;

@Service
public class WishlistServiceImpl implements WishlistService{
	
	@Autowired
	private WishlistRepositery wishlistRepositery;

	@Override
	public Wishlist addProductToWishlist(Wishlist wishlist) {
		Wishlist w = wishlistRepositery.save(wishlist);
		return w;
	}

	@Override
	public void removeProductToWishlist(int userId, int p) {
		Wishlist w = this.getWishListId(userId, p);
		wishlistRepositery.deleteWishlistById(w.getId());		
	}

	@Override
	public Wishlist getById(int id) {
		return wishlistRepositery.findById(id).get();
	}

	@Override
	public List<Wishlist> getAllWishlistProduct(int userId,String pageNumber) {
		User user = new User();
		user.setId(userId);
		Pageable p = PageRequest.of(Integer.parseInt(pageNumber), 20);
		return wishlistRepositery.findByUser(user,p);
	}

	@Override
	public boolean isProductintoWishList(int userId, int productId) {
		User user = new User();
		user.setId(userId);
		List<Wishlist> l = wishlistRepositery.isProductintoWishList(productId, user);
		return l.isEmpty()?false:true;
	}

	@Override
	public Wishlist getWishListId(int userId, int productId) {
		User user = new User();
		user.setId(userId);
		return wishlistRepositery.isProductintoWishList(productId, user).get(0);
	}

}
