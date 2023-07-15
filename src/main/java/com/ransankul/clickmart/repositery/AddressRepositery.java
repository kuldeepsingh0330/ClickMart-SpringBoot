package com.ransankul.clickmart.repositery;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ransankul.clickmart.model.Address;
import com.ransankul.clickmart.model.User;
import java.util.List;

public interface AddressRepositery extends JpaRepository<Address, Integer> {


    public List<Address> findByuser(User user);
    
    public List<Address> findByAddressId(int addressId);

        
}
