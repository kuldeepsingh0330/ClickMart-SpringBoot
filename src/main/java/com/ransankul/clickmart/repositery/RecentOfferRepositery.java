package com.ransankul.clickmart.repositery;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ransankul.clickmart.model.RecentOffer;

public interface RecentOfferRepositery extends JpaRepository<RecentOffer,Integer>{
	
	@Query("SELECT ro FROM RecentOffer ro ORDER BY ro.id DESC")
	List<RecentOffer> findLastFive(Pageable pageable);

}
