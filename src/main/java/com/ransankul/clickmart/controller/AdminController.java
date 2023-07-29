package com.ransankul.clickmart.controller;

import java.io.File;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ransankul.clickmart.exception.ResourceNotFoundException;
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

	@Autowired
	private ObjectMapper mapper;
	@Value("${project.image}")
    private String path;

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
		m.addAttribute("count",adminService.getCountProduct());
		m.addAttribute("outofstock",adminService.getCountoutOfStockProduct());
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
    
    @PostMapping("/category/{categoryId}")
    public ResponseEntity<Category> getCategoryByID(@PathVariable int categoryId) {
        Category category = adminService.getCategoryByID(categoryId);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            throw new ResourceNotFoundException("No category found with this ID" + categoryId);
        }
    }

	@PostMapping("/category/add")
	public ResponseEntity<String> addCategory(@RequestParam("category") String cat, @RequestParam("iconFile") MultipartFile iconFile) {

		if (!iconFile.isEmpty()) {
			try {
				
				Category category = mapper.readValue(cat,Category.class);
				String fileName = iconFile.getOriginalFilename();
				Path filePath = Paths.get(path+File.separator+"categoryImage", fileName);
				Files.write(filePath, iconFile.getBytes());
	
				category.setIcon(fileName);
				adminService.addCategory(category);
				return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
			}catch(JsonProcessingException exception){
				return new ResponseEntity<>("provided invalid data", HttpStatus.BAD_REQUEST);
							
			}catch(IllegalArgumentException exception){
				return new ResponseEntity<>("Category already exist with this name", HttpStatus.BAD_REQUEST);
							
			}
			catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
		return new ResponseEntity<>("Icon file is empty", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/category/update")
	public ResponseEntity<String> updateCategory(@RequestParam("category") String cat, @RequestParam("iconFile") MultipartFile iconFile) {

		if (!iconFile.isEmpty()) {
			try {
				
				Category category = mapper.readValue(cat,Category.class);
				if(category.getId() == 0) throw new IllegalArgumentException();
				String fileName = iconFile.getOriginalFilename();
				Path filePath = Paths.get(path+File.separator+"categoryImage", fileName);
				Files.write(filePath, iconFile.getBytes());

				Category existCategory = adminService.getCategoryByID(category.getId());
				category.setCreatedAt(existCategory.getCreatedAt());
				category.setLastUpdate(System.currentTimeMillis());
				category.setIcon(fileName);
				
				adminService.updateCategory(category);
				return new ResponseEntity<>("Category updated succesfully", HttpStatus.OK);
			}catch(JsonProcessingException exception){
				return new ResponseEntity<>("provided invalid data", HttpStatus.BAD_REQUEST);
							
			}catch(IllegalArgumentException exception){
				return new ResponseEntity<>("provided data is invalid", HttpStatus.BAD_REQUEST);
							
			}
			catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
		return new ResponseEntity<>("Icon file is empty", HttpStatus.BAD_REQUEST);

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
		@PathVariable int categoryId) {
		adminService.changeCategoryVisibility(categoryId);
	        return new ResponseEntity<>("Category visibility changed successfully", HttpStatus.OK);
	}
	
	
	//PRODUCTS-----------------------

	@PostMapping("/product/all/{pageNumber}")
	public ResponseEntity<APIResponse<List<Category>>> getProduct(@PathVariable String pageNumber) {
		List<Product> product = adminService.getProduct(Integer.parseInt(pageNumber));
		if(product.isEmpty()){
			return new ResponseEntity<APIResponse<List<Category>>>(new APIResponse<>("201", null, "No product found"), HttpStatus.OK);
		}
		
		APIResponse<List<Category>> response = new APIResponse("200",product,"");
		return new ResponseEntity<APIResponse<List<Category>>>(response, HttpStatus.OK);
	}
	
    @PostMapping("/product/")
    public ResponseEntity<String> addProduct(@Valid @RequestBody Product product) {
        adminService.addProduct(product);
        return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
    }


	@PostMapping("/product/{id}")
    public ResponseEntity<APIResponse<Product>> getProductById(@PathVariable int id) {
        Product product = adminService.getProductById(id);
        return new ResponseEntity<APIResponse<Product>>(new APIResponse<>("201", product, ""), HttpStatus.CREATED);
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
