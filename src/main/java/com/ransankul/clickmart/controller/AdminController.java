package com.ransankul.clickmart.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import com.ransankul.clickmart.model.APIResponse;
import com.ransankul.clickmart.model.Category;
import com.ransankul.clickmart.model.Product;
import com.ransankul.clickmart.model.Transaction;
import com.ransankul.clickmart.model.User;
import com.ransankul.clickmart.payloads.TransactionResponse;
import com.ransankul.clickmart.security.JWTAuthResponse;
import com.ransankul.clickmart.security.JWTTokenHelper;
import com.ransankul.clickmart.service.AdminService;
import com.ransankul.clickmart.service.TransactionService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
// @PreAuthorize("hasRole('ROLE_ADMIN_USER')")
public class AdminController {
	
	@Autowired
	private AdminService adminService;


	    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

	@RequestMapping("/home")
	public String home(Model m){
		m.addAttribute("title","Home - ClickMart");
		return "/home";
	}
    
    @RequestMapping("/login")
	public String login(Model m){
		m.addAttribute("title","Login - ClickMart");
		return "login";
	}

	@RequestMapping("/category")
	public String category(Model m){
		m.addAttribute("totalCategory","50");
		m.addAttribute("title","Categoey - ClickMart");
		m.addAttribute("count",adminService.getCount());
		m.addAttribute("countPublic",adminService.getCountPublic());
		m.addAttribute("countPrivate",adminService.getCountPrivate());
		return "category";
	}

	@RequestMapping("/product")
	public String product(Model m){
		m.addAttribute("title","Product - ClickMart");
		return "product";
	}

	@RequestMapping("/order")
	public String order(Model m){
		m.addAttribute("title","Order - ClickMart");
		return "order";
	}

	@RequestMapping("/feedback")
	public String feedback(Model m){
		m.addAttribute("title","Feedback - ClickMart");
		return "feedback";
	}

	@RequestMapping("/help")
	public String help(Model m){
		m.addAttribute("title","Help - ClickMart");
		return "help";
	}


	// vallidation

	@RequestMapping(path = "/login/token", method = RequestMethod.POST)
    public String login(HttpServletRequest request,Model m, HttpServletResponse response){

    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
        this.doAuthenticate(username,password);
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = this.jwtTokenHelper.generateToken(userDetails);
		Cookie c = new Cookie("JWTtoken", token);
		c.setMaxAge(-1);
		c.setPath("/");
		response.addCookie(c);
        
        return "redirect:/admin/redirect";
    }

	@RequestMapping(path = "/redirect")
    public String redirect(HttpServletRequest request){
		return "redirect";
    }

    private void doAuthenticate(String username, String password){
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,password);
        try{
            manager.authenticate(authentication);
        }catch(BadCredentialsException e){
            throw new RuntimeException("Invalid username and password");
        }
    }

	@PostMapping("/category/add")
	public ResponseEntity<String> addCategory(@Valid @RequestBody Category category) {
		Category cat = adminService.addCategory(category);
		System.out.println(cat.getIsPublic());
		if(cat != null)
			return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
		else
			return new ResponseEntity<String>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/category/all/{pageNumber}")
	public ResponseEntity<APIResponse<List<Category>>> getCategory(@PathVariable String pageNumber) {
		List<Category> cat = adminService.getCategory(Integer.parseInt(pageNumber));
		if(cat.isEmpty()){
			return new ResponseEntity<APIResponse<List<Category>>>(new APIResponse<>("201", null, "No category found"), HttpStatus.OK);
		}
		
		APIResponse<List<Category>> response = new APIResponse("200",cat,"");
		return new ResponseEntity<APIResponse<List<Category>>>(response, HttpStatus.OK);
	}

	@DeleteMapping("category/rm/{categoryId}")
	public ResponseEntity<String> removeCategory(@PathVariable int categoryId) {
		adminService.removeCategory(categoryId);
	    return new ResponseEntity<>("Category removed successfully", HttpStatus.OK);
	}

	@PutMapping("category/vis/{categoryId}")
	public ResponseEntity<String> changeCategoryVisibility(
		@PathVariable int categoryId,
	    @RequestParam("visible") boolean visible) {
		adminService.changeCategoryVisibility(categoryId, visible);
	        return new ResponseEntity<>("Category visibility changed successfully", HttpStatus.OK);
	}
	
	
	//PRODUCTS-----------------------

	@PostMapping("/category/")
	public ResponseEntity<APIResponse<List<Category>>> getProduct(@PathVariable String pageNumber) {
		List<Category> product = adminService.getCategory(Integer.parseInt(pageNumber));
		if(product.isEmpty()){
			return new ResponseEntity<APIResponse<List<Category>>>(new APIResponse<>("201", null, "No product found"), HttpStatus.OK);
		}
		
		APIResponse<List<Category>> response = new APIResponse("200",product,"");
		return new ResponseEntity<APIResponse<List<Category>>>(response, HttpStatus.OK);
	}
	
    @PostMapping("product/")
    public ResponseEntity<String> addProduct(@Valid @RequestBody Product product) {
        adminService.addProduct(product);
        return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("product/rm/{productId}")
    public ResponseEntity<String> removeProduct(@PathVariable int productId) {
    	adminService.removeProduct(productId);
        return new ResponseEntity<>("Product removed successfully", HttpStatus.OK);
    }

    @PutMapping("product/update/{productId}")
    public ResponseEntity<String> updateProduct(
            @PathVariable int productId,
            @Valid @RequestBody Product product) {
    	Product pro = adminService.updateProduct(productId, product);
    	if(pro != null)
    		return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
    	else
			return new ResponseEntity<String>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
					
    }

	//Transaction && Order

	@PostMapping("/order/{pageNumber}")
    public ResponseEntity<APIResponse<List<TransactionResponse>>> getAllPayments(@PathVariable String pageNumber,HttpServletRequest request){
        	List<Transaction> list = adminService.getAllTransaction(pageNumber);
        	List<TransactionResponse> response = new ArrayList<>();
        	for(Transaction tr : list) {
        		String status = tr.getStatus();
        		Date orderTime = tr.getCreated_at();
        		String transactionId = tr.getOrder_id();
        		
        		for(Product p : tr.getOrderedProduct()) {
        			TransactionResponse tResponse = new TransactionResponse();
        			tResponse.setImageName(p.getImages().get(0));
        			tResponse.setProductId(String.valueOf(p.getProductId()));
        			tResponse.setProductName(p.getName());
        			tResponse.setOrderTime(orderTime);
        			tResponse.setPaymentStatus(status);
        			tResponse.setTransactionId(transactionId);
        			
        			response.add(tResponse);
        		}
        	}
        	
        	return new ResponseEntity<APIResponse<List<TransactionResponse>>>(new APIResponse<>("200",response,"OK"),HttpStatus.OK);
        
    }
    
}
