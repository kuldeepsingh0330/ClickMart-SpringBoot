package com.ransankul.clickmart.serviceImpl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ransankul.clickmart.model.Address;
import com.ransankul.clickmart.model.User;
import com.ransankul.clickmart.repositery.AddressRepositery;
import com.ransankul.clickmart.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepositery addressRepositery;
    

    @Override
    public Address getAddressById(int addressId) {
        return addressRepositery.findByAddressId(addressId).get(0);
    }


    @Override
    public List<Address> getAddressByUserId(User user,String pageNumber) {
    	Pageable p = PageRequest.of(Integer.parseInt(pageNumber), 7);
        return addressRepositery.findByuser(user,p);
    }


	@Override
	public Address addAddress(Address address) {
		Address add =  addressRepositery.save(address);
		return add;
	}


	@Override
	public boolean removeAddress(int id, int userid) {
		Address address= addressRepositery.findById(id).get();
		
		
        if (address == null) {
        	return false;
        }else if(address.getUser().getId() == userid){
        	addressRepositery.delete(address);  
        	return true;
        }
        return false;
	}


	@Override
	public Address updateAddress(Address address) {
		Address add = addressRepositery.findById(address.getAddressId()).get();
		
        if (add == null) {
            throw new IllegalArgumentException("address does not exist");
        }else {
        	return addressRepositery.save(address);      	
        }
	}
    
}
