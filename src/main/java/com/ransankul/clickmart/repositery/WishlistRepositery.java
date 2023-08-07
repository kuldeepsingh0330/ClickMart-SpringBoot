package com.ransankul.clickmart.repositery;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ransankul.clickmart.model.Product;
import com.ransankul.clickmart.model.User;
import com.ransankul.clickmart.model.Wishlist;

import jakarta.transaction.Transactional;

public interface WishlistRepositery extends JpaRepository<Wishlist, Integer>{
	
	public List<Wishlist> findByUser(User user,Pageable p);
	
    @Query("SELECT w FROM Wishlist w WHERE w.user = :userId and w.productId = :productId")
    List<Wishlist> isProductintoWishList(int productId, User userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Wishlist w WHERE w.id = :id")
    void deleteWishlistById(int id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Wishlist w WHERE w.productId = :productId")
    void removeEntityWhichNotWxist(int productId);

}
