package com.ransankul.clickmart.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ransankul.clickmart.exception.ResourceNotFoundException;
import com.ransankul.clickmart.model.APIResponse;
import com.ransankul.clickmart.model.Address;
import com.ransankul.clickmart.model.Product;
import com.ransankul.clickmart.model.User;
import com.ransankul.clickmart.model.Wishlist;
import com.ransankul.clickmart.payloads.AddressResponse;
import com.ransankul.clickmart.security.JWTTokenHelper;
import com.ransankul.clickmart.service.AddressService;
import com.ransankul.clickmart.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;
    
    @PostMapping("/")
    public ResponseEntity<?> addAddress(@Valid @RequestBody Address address) {
        Address addedAddress = addressService.addAddress(address);
        if(addedAddress != null) {
        	APIResponse<Address> apires = new APIResponse<Address>("201",addedAddress,"Address added succesfully");
        	return new ResponseEntity<>(apires, HttpStatus.CREATED);
        }
        else
        	return new ResponseEntity(new APIResponse<>("500",null,"Something went wrong"),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<String> removeAddress(@PathVariable int addressId) {
        addressService.removeAddress(addressId);
        return new ResponseEntity<>("Address removed successfully", HttpStatus.OK);
    }

    @PutMapping("/updateAddress")
    public ResponseEntity<APIResponse<Address>> updateAddress(@Valid @RequestBody Address address) {
//    	if(address.getUser() == null) {
//            return new ResponseEntity<>("Invalid request please add user Id also with request", HttpStatus.BAD_REQUEST);    		
//    	}
    	addressService.updateAddress(address);
        return new ResponseEntity<APIResponse<Address>>(new APIResponse<>("201",null,"updated succesfully"),HttpStatus.CREATED);
    }
    
    @PostMapping("/{addressId}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable int addressId) {
        Address ad = addressService.getAddressById(addressId);
        AddressResponse addressResponse = new AddressResponse(ad.getAddressId(),
				ad.getStreet(),ad.getCity(),ad.getState(),ad.getPostalCode(),ad.getCountry());
	        	
        return ResponseEntity.ok(addressResponse);
    }
    
    @PostMapping("/allAddress")
    public ResponseEntity<APIResponse<List<AddressResponse>>> getAddressByUserId(HttpServletRequest request) {
    	
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String userName = jwtTokenHelper.extractUsername(token);
        	User user = userService.finduserByUsername(userName);
            List<Address> addresses = addressService.getAddressByUserId(user);
            if(addresses.size()>0) {
            	List<AddressResponse> addressesPayload = new ArrayList<>();
            	for(Address ad : addresses) {
            		addressesPayload.add(new AddressResponse(ad.getAddressId(),
            				ad.getStreet(),ad.getCity(),ad.getState(),ad.getPostalCode(),ad.getCountry()));
            	}
                return new ResponseEntity<APIResponse<List<AddressResponse>>>(new APIResponse<>("201",addressesPayload,"Saved Address"),HttpStatus.CREATED);
            }
            else 
            	return new ResponseEntity<>(new APIResponse<>("200",null,"NO address found"),HttpStatus.OK);
                

        }else {
        	return new ResponseEntity<>(new APIResponse<>("400",null,"Headers is missing") ,HttpStatus.BAD_REQUEST);        	
        }
        
    }
}
