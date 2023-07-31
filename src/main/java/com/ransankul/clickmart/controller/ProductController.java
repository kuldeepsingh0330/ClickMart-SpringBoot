package com.ransankul.clickmart.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ransankul.clickmart.exception.ResourceNotFoundException;
import com.ransankul.clickmart.model.APIResponse;
import com.ransankul.clickmart.model.Category;
import com.ransankul.clickmart.model.Product;
import com.ransankul.clickmart.model.User;
import com.ransankul.clickmart.model.Wishlist;
import com.ransankul.clickmart.security.JWTTokenHelper;
import com.ransankul.clickmart.service.ProductService;
import com.ransankul.clickmart.service.UserService;
import com.ransankul.clickmart.service.WishlistService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/product")
public class ProductController {
    
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private WishlistService wishlistService;
    
    @Value("${project.image}")
    private String path;
    
    @Autowired
    private JWTTokenHelper jwtTokenHelper;
	

    @GetMapping("getProductsByCategory/{categoryId}")
    public List<Product> getProductsByCategory(@PathVariable int categoryId) {
        Category category = new Category();
        category.setId(categoryId);
        List<Product> list =  productService.getproductbyCategory(category);
        if(list.size()>0) return list;
        else 
            throw new ResourceNotFoundException("No product found in this category" + categoryId);
    }

    @GetMapping("getProductById/{productId}")
    public Product getProductById(@PathVariable int productId) {
        Product product =  productService.getProductById(productId);
        if(product != null) return product;
        else 
            throw new ResourceNotFoundException("No product found with this ID" + productId);
    }

    @GetMapping("/all/{pageNumber}")
    public List<Product> getAllProducts(@PathVariable(required = false)String pageNumber) {
        List<Product> list =  productService.getAllProduct(pageNumber);
        if(list.size()>0) return list;
        else 
            throw new ResourceNotFoundException("No product found");
    }
    
    @GetMapping("/search/{pageNumber}")
    public List<Product> searchProductByName(@RequestParam String name,@PathVariable String pageNumber) {
        List<Product> list =  productService.searchProductByName(name,pageNumber);
        System.out.println(list.size()+" "+name);
        if(list.size()>0) return list;
        else 
            throw new ResourceNotFoundException("No product found");
    }
    
    @GetMapping("/image/{productId}")
    public ResponseEntity<Resource> productDisplayImage(@PathVariable int productId) throws MalformedURLException {
    	String folderPath = path+"productImage";
    	String imageName = productService.displayImage(productId);
    	
        Path imagePath = Paths.get(folderPath).resolve(imageName);
        Resource imageResource = new UrlResource(imagePath.toUri());

        if (imageResource.exists() && imageResource.isReadable()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); 
        
            return ResponseEntity.ok().headers(headers).body(imageResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    	
    }
    
    @GetMapping("/image/{productId}/{imageName}")
    public ResponseEntity<Resource> getProductImageByName(@PathVariable int productId, @PathVariable String imageName) throws MalformedURLException {
    	String folderPath = path+"productImage";
    	
        Path imagePath = Paths.get(folderPath).resolve(imageName);
        Resource imageResource = new UrlResource(imagePath.toUri());

        if (imageResource.exists() && imageResource.isReadable()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); 
        
            return ResponseEntity.ok().headers(headers).body(imageResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    	
    }
    
    
    @PostMapping("wishlist/add")
    public ResponseEntity<APIResponse<Wishlist>> addProductTOWishlist(@RequestBody Wishlist wishlist, HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String userName = jwtTokenHelper.extractUsername(token);
        	User user = userService.finduserByUsername(userName);
            wishlist.setUser(user);
        	Wishlist w = wishlistService.addProductToWishlist(wishlist);
        	w.setUser(null);
        	return new ResponseEntity<>(new APIResponse<>("201",w,"Added successfully") ,HttpStatus.CREATED);
       
        }else {
        	return new ResponseEntity<>(new APIResponse<>("400",null,"Headers is missing") ,HttpStatus.BAD_REQUEST);        	
        } 	
    }
    
    @PostMapping("wishlist/remove/{id}")
    public ResponseEntity<APIResponse<String>> removeeProductTOWishlist(@PathVariable int id, HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String userName = jwtTokenHelper.extractUsername(token);
        	User user = userService.finduserByUsername(userName);
        	int w = wishlistService.getWishListId(user.getId(), id);
        	wishlistService.removeProductToWishlist(w);
        	return new ResponseEntity<>(new APIResponse<>("202","","removed successfully"),HttpStatus.ACCEPTED);	
    		
        }else {
        	return new ResponseEntity<>(new APIResponse<>("400",null,"Headers is missing") ,HttpStatus.BAD_REQUEST);        	
        }
    }
    
    @PostMapping("wishlist/all/{pageNumber}")
    public ResponseEntity<APIResponse<List<Product>>> getAllWishlistProduct(@PathVariable String pageNumber, HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String userName = jwtTokenHelper.extractUsername(token);
        	User user = userService.finduserByUsername(userName);
        	List<Wishlist> w = wishlistService.getAllWishlistProduct(user.getId(),pageNumber);
        	List<Product> p = new ArrayList<>();
        	for(Wishlist wl : w) {
        		p.add(productService.getProductById(wl.getProductId()));
        	}
        	
        	
        	if(!w.isEmpty()) {
            	return new ResponseEntity<>(new APIResponse<>("201",p,"removed successfully"),HttpStatus.CREATED);	
        	}else {
            	return new ResponseEntity<>(new APIResponse<>("200",null,"No item is into wishlist"),HttpStatus.OK);        		
        	}
        }else {
        	return new ResponseEntity<>(new APIResponse<>("400",null,"Headers is missing") ,HttpStatus.BAD_REQUEST);        	
        }
    }
    
    @PostMapping("wishlist/isExist/{id}")
    public ResponseEntity<APIResponse<Boolean>> isProductintoWishList(@PathVariable int id, HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String userName = jwtTokenHelper.extractUsername(token);
        	User user = userService.finduserByUsername(userName);
        	boolean w = wishlistService.isProductintoWishList(user.getId(),id);	
        	
        	if(w) {
            	return new ResponseEntity<>(new APIResponse<>("201",w,"Exist"),HttpStatus.CREATED);	
        	}else {
            	return new ResponseEntity<>(new APIResponse<>("200",w,"Not exist"),HttpStatus.OK);        		
        	}
        }else {
        	return new ResponseEntity<>(new APIResponse<>("400",null,"Headers is missing") ,HttpStatus.BAD_REQUEST);        	
        }
    }
    
    
}