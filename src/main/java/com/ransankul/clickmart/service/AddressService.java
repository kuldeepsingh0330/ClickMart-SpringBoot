package com.ransankul.clickmart.service;


import com.ransankul.clickmart.model.Address;
import com.ransankul.clickmart.model.User;
import java.util.List;

public interface AddressService {

    //getAddressById
    public Address getAddressById(int addressId);

    //getAddressByUserId
    public List<Address> getAddressByUserId(User user);

}
