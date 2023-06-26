package com.ransankul.clickmart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ransankul.clickmart.exception.ResourceNotFoundException;
import com.ransankul.clickmart.model.Address;
import com.ransankul.clickmart.model.User;
import com.ransankul.clickmart.service.AddressService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;
    
    @PostMapping
    public ResponseEntity<String> addAddress(@Valid @RequestBody Address address) {
        Address addedAddress = addressService.addAddress(address);
        if(addedAddress != null)
        	return new ResponseEntity<>("Address added succesfully", HttpStatus.CREATED);
        else
        	return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<String> removeAddress(@PathVariable int addressId) {
        addressService.removeAddress(addressId);
        return new ResponseEntity<>("Address removed successfully", HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateAddress(@Valid @RequestBody Address address) {
    	if(address.getUser() == null) {
            return new ResponseEntity<>("Invalid request please add user Id also with request", HttpStatus.BAD_REQUEST);    		
    	}
    	addressService.updateAddress(address);
        return new ResponseEntity<>("Address updated succesfully", HttpStatus.OK);
    }
    
    @GetMapping("/{addressId}")
    public ResponseEntity<Address> getAddressById(@PathVariable int addressId) {
        Address address = addressService.getAddressById(addressId);
        if (address == null) {
            throw new ResourceNotFoundException("No address found with this ID" + addressId);
        }
        return ResponseEntity.ok(address);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Address>> getAddressByUserId(@PathVariable int userId) {
        User user = new User();
        user.setId(userId);
        List<Address> addresses = addressService.getAddressByUserId(user);
        if (addresses.isEmpty()) {
            throw new ResourceNotFoundException("No address found with this userID" + userId);
        }
        return ResponseEntity.ok(addresses);
    }
    
}
