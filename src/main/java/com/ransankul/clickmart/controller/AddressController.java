package com.ransankul.clickmart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ransankul.clickmart.model.Address;
import com.ransankul.clickmart.model.User;
import com.ransankul.clickmart.service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;
    
    @GetMapping("/{addressId}")
    public ResponseEntity<Address> getAddressById(@PathVariable int addressId) {
        Address address = addressService.getAddressById(addressId);
        if (address == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(address);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Address>> getAddressByUserId(@PathVariable int userId) {
        User user = new User();
        user.setId(userId);
        List<Address> addresses = addressService.getAddressByUserId(user);
        if (addresses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(addresses);
    }
    
}
