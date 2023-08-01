package com.ransankul.clickmart.repositery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ransankul.clickmart.model.Help;

public interface HelpRepositery extends JpaRepository<Help,Long>{

    Page<Help> findByResolvedFalse(Pageable pageable);
        
}
