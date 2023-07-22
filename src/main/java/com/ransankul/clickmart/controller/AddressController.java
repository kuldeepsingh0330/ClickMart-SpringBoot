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
    public ResponseEntity<APIResponse<AddressResponse>> addAddress(@Valid @RequestBody Address address, HttpServletRequest request) {
    	
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String userName = jwtTokenHelper.extractUsername(token);
        	User user = userService.finduserByUsername(userName);
        	address.setUser(user);
            Address ad = addressService.addAddress(address);
            AddressResponse response = new AddressResponse(ad.getAddressId(),
    				ad.getStreet(),ad.getCity(),ad.getState(),
    				ad.getPostalCode(),ad.getCountry());
            
            APIResponse<AddressResponse> apires = new APIResponse<>("201",response,"Address added succesfully");
            return new ResponseEntity<>(apires, HttpStatus.CREATED);
        	
        }else {
        	return new ResponseEntity<>(new APIResponse<>("400",null,"Headers is missing") ,HttpStatus.BAD_REQUEST);        	
        }
    	

    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<APIResponse<String>> removeAddress(@PathVariable int addressId, HttpServletRequest request) {
    	
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String userName = jwtTokenHelper.extractUsername(token);
        	User user = userService.finduserByUsername(userName);
        	
            boolean b = addressService.removeAddress(addressId,user.getId());
            
            if(b) {            
            	APIResponse<String> apires = new APIResponse<>("201",null,"Address removed successfully");
            	return new ResponseEntity<>(apires, HttpStatus.CREATED);
            }else {
            	APIResponse<String> apires = new APIResponse<>("400",null,"Address doesn't exist");
            	return new ResponseEntity<>(apires, HttpStatus.BAD_REQUEST);
            }
        }else {
        	return new ResponseEntity<>(new APIResponse<>("400",null,"Headers is missing") ,HttpStatus.BAD_REQUEST);        	
        }
    }

    @PutMapping("/updateAddress")
    public ResponseEntity<APIResponse<AddressResponse>> updateAddress(@Valid @RequestBody Address address, HttpServletRequest request) {
    	
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String userName = jwtTokenHelper.extractUsername(token);
        	User user = userService.finduserByUsername(userName);
        	address.setUser(user);
        	Address ad = addressService.updateAddress(address);
            AddressResponse response = new AddressResponse(ad.getAddressId(),
    				ad.getStreet(),ad.getCity(),ad.getState(),
    				ad.getPostalCode(),ad.getCountry());
            
            APIResponse<AddressResponse> apires = new APIResponse<>("201",response,"Address updated succesfully");
            return new ResponseEntity<>(apires, HttpStatus.CREATED);
        	
        }else {
        	return new ResponseEntity<>(new APIResponse<>("400",null,"Headers is missing") ,HttpStatus.BAD_REQUEST);        	
        }
        
    }

    
    @PostMapping("/allAddress/{pageNumber}")
    public ResponseEntity<APIResponse<List<AddressResponse>>> getAddressByUserId(@PathVariable String pageNumber,HttpServletRequest request) {
    	
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String userName = jwtTokenHelper.extractUsername(token);
        	User user = userService.finduserByUsername(userName);
            List<Address> addresses = addressService.getAddressByUserId(user,pageNumber);
            
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
