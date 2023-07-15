package com.ransankul.clickmart.repositery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ransankul.clickmart.model.User;
import com.ransankul.clickmart.model.Wishlist;

public interface WishlistRepositery extends JpaRepository<Wishlist, Integer>{
	
	public List<Wishlist> findByUser(User user);
	
    @Query("SELECT w FROM Wishlist w WHERE w.user = :userId and w.productId = :productId")
    List<Wishlist> isProductintoWishList(int productId, User userId);

}
