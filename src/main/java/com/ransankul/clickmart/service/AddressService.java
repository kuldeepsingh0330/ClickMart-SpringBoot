package com.ransankul.clickmart.service;

import java.util.List;

import com.ransankul.clickmart.model.Address;

public interface AddressService {
    

    //getAddressByUserID
    public List<Address> getAddressByUserID(int userId);

    //getAddressById
    public Address getAddressById(int addressId);

}
