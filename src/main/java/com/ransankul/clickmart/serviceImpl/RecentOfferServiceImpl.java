package com.ransankul.clickmart.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ransankul.clickmart.model.RecentOffer;
import com.ransankul.clickmart.repositery.RecentOfferRepositery;
import com.ransankul.clickmart.service.RecentOfferService;

@Service
public class RecentOfferServiceImpl implements RecentOfferService{
	
	@Autowired
	private RecentOfferRepositery recentOfferRepositery;

	@Override
	public List<RecentOffer> recentOffer() {
		
		PageRequest pageRequest = PageRequest.of(0, 5); // Limiting to 5 elements
		return this.recentOfferRepositery.findLastFive(pageRequest);
		
	}

}
