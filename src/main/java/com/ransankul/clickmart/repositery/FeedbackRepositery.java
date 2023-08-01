package com.ransankul.clickmart.repositery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ransankul.clickmart.model.Feedback;

public interface FeedbackRepositery extends JpaRepository<Feedback, Integer> {
    
}
