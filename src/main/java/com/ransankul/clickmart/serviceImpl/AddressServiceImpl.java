package com.ransankul.clickmart.serviceImpl;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ransankul.clickmart.model.Address;
import com.ransankul.clickmart.repositery.AddressRepositery;
import com.ransankul.clickmart.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepositery addressRepositery;
    

    @Override
    public Address getAddressById(int addressId) {
        return addressRepositery.findById(addressId)
                .orElseThrow(() -> new NoSuchElementException("Address not found with ID: " + addressId));
    }
    
}
