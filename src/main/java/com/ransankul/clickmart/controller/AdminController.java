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
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.ransankul.clickmart.model.Feedback;
import com.ransankul.clickmart.model.Help;
import com.ransankul.clickmart.model.Product;
import com.ransankul.clickmart.model.Transaction;
import com.ransankul.clickmart.model.User;
import com.ransankul.clickmart.payloads.TransactionAdminResponse;
import com.ransankul.clickmart.payloads.TransactionResponse;
import com.ransankul.clickmart.security.JWTAuthResponse;
import com.ransankul.clickmart.security.JWTTokenHelper;
import com.ransankul.clickmart.service.AdminService;
import com.ransankul.clickmart.service.TransactionService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
	private JWTTokenHelper jwtTokenHelper;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ObjectMapper mapper;
	@Value("${project.image}")
	private String path;

	@RequestMapping("/login")
	public String login(Model m) {
		m.addAttribute("title", "Login - ClickMart");
		return "login";
	}

	@RequestMapping("/category")
	public String category(Model m) {
		m.addAttribute("totalCategory", "50");
		m.addAttribute("title", "Categoey - ClickMart");
		m.addAttribute("count", adminService.getCount());
		m.addAttribute("countPublic", adminService.getCountPublic());
		m.addAttribute("countPrivate", adminService.getCountPrivate());
		return "category";
	}

	@RequestMapping("/product")
	public String product(Model m) {
		m.addAttribute("title", "Product - ClickMart");
		m.addAttribute("count", adminService.getCountProduct());
		m.addAttribute("outofstock", adminService.getCountoutOfStockProduct());
		return "product";
	}

	@RequestMapping("/order")
	public String order(Model m) {
		m.addAttribute("title", "Order - ClickMart");
		return "order";
	}

	@RequestMapping("/feedback")
	public String feedback(Model m) {
		m.addAttribute("title", "Feedback - ClickMart");
		return "feedback";
	}

	// vallidation

	@RequestMapping(path = "/login/token", method = RequestMethod.POST)
	public String login(@RequestParam("username") String username,
			@RequestParam("password") String password,
			HttpServletResponse response,
			Model m) {

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

		// Create JWT token and set it as a cookie
		String token = this.jwtTokenHelper.generateToken(userDetails);
		Cookie c = new Cookie("JWTtoken", token);
		c.setMaxAge(-1);
		c.setPath("/");
		response.addCookie(c);
		return "redirect";
	}

	@RequestMapping("/redirect")
	public String redirect() {
		return "redirect";
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
	public ResponseEntity<String> addCategory(@RequestParam("category") String cat,
			@RequestParam("iconFile") MultipartFile iconFile) {

		if (!iconFile.isEmpty()) {
			try {

				Category category = mapper.readValue(cat, Category.class);
				String fileName = iconFile.getOriginalFilename();
				Path filePath = Paths.get(path + File.separator + "categoryImage", fileName);
				Files.write(filePath, iconFile.getBytes());

				category.setIcon(fileName);
				adminService.addCategory(category);
				return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
			} catch (JsonProcessingException exception) {
				return new ResponseEntity<>("provided invalid data", HttpStatus.BAD_REQUEST);

			} catch (IllegalArgumentException exception) {
				return new ResponseEntity<>("Category already exist with this name", HttpStatus.BAD_REQUEST);

			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<>("Icon file is empty", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/category/update")
	public ResponseEntity<String> updateCategory(@RequestParam("category") String cat,
			@RequestParam("iconFile") MultipartFile iconFile) {

		if (!iconFile.isEmpty()) {
			try {

				Category category = mapper.readValue(cat, Category.class);
				if (category.getId() == 0)
					throw new IllegalArgumentException();
				String fileName = iconFile.getOriginalFilename();
				Path filePath = Paths.get(path + File.separator + "categoryImage", fileName);
				Files.write(filePath, iconFile.getBytes());

				Category existCategory = adminService.getCategoryByID(category.getId());
				category.setCreatedAt(existCategory.getCreatedAt());
				category.setLastUpdate(System.currentTimeMillis());
				category.setIcon(fileName);

				adminService.updateCategory(category);
				return new ResponseEntity<>("Category updated succesfully", HttpStatus.OK);
			} catch (JsonProcessingException exception) {
				return new ResponseEntity<>("provided invalid data", HttpStatus.BAD_REQUEST);

			} catch (IllegalArgumentException exception) {
				return new ResponseEntity<>("provided data is invalid", HttpStatus.BAD_REQUEST);

			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<>("Icon file is empty", HttpStatus.BAD_REQUEST);

	}

	@PostMapping("/category/all/{pageNumber}")
	public ResponseEntity<APIResponse<List<Category>>> getCategory(@PathVariable String pageNumber) {
		List<Category> cat = adminService.getCategory(Integer.parseInt(pageNumber));
		if (cat.isEmpty()) {
			return new ResponseEntity<APIResponse<List<Category>>>(new APIResponse<>("201", null, "No category found"),
					HttpStatus.OK);
		}

		APIResponse<List<Category>> response = new APIResponse("200", cat, "");
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

	// PRODUCTS-----------------------

	@PostMapping("/product/all/{pageNumber}")
	public ResponseEntity<APIResponse<List<Category>>> getProduct(@PathVariable String pageNumber) {
		List<Product> product = adminService.getProduct(Integer.parseInt(pageNumber));
		if (product.isEmpty()) {
			return new ResponseEntity<APIResponse<List<Category>>>(new APIResponse<>("201", null, "No product found"),
					HttpStatus.OK);
		}

		APIResponse<List<Category>> response = new APIResponse("200", product, "");
		return new ResponseEntity<APIResponse<List<Category>>>(response, HttpStatus.OK);
	}

	@PostMapping("/product/add")
	public ResponseEntity<String> addProduct(@RequestParam("category") String cat,
			@RequestParam("iconFile") List<MultipartFile> iconFile) {
		if (!iconFile.isEmpty()) {
			try {

				Product product = mapper.readValue(cat, Product.class);
				List<String> fileNameList = new ArrayList<>();

				Product pro = adminService.addProduct(product);

				String folderPath = path + "productImage";

				for (MultipartFile file : iconFile) {
					String fileName = file.getOriginalFilename();

					Path filePath = Paths.get(folderPath, fileName);
					Files.write(filePath, file.getBytes());
					fileNameList.add(fileName);
				}
				pro.setImages(fileNameList);

				adminService.updateProduct(pro);
				return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
			} catch (JsonProcessingException exception) {
				return new ResponseEntity<>("provided invalid data", HttpStatus.BAD_REQUEST);

			} catch (IllegalArgumentException exception) {
				return new ResponseEntity<>("Category already exist with this name", HttpStatus.BAD_REQUEST);

			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<>("Images is required", HttpStatus.BAD_REQUEST);
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

	@PutMapping("category/ava/{productId}")
	public ResponseEntity<String> changeProductAvailibality(
			@PathVariable int productId) {
		boolean isChange = adminService.changeProductAvailibality(productId);
		if (isChange)
			return new ResponseEntity<>("Product Availability changed successfully", HttpStatus.OK);
		else
			return new ResponseEntity<>("Something went wrong", HttpStatus.OK);
	}

	@PutMapping("category/qua/{productId}/{quantity}")
	public ResponseEntity<String> addMoreProductQuantity(
			@PathVariable int productId, @PathVariable int quantity) {
		boolean isChange = adminService.addMoreProductQuantity(productId, quantity);
		if (isChange)
			return new ResponseEntity<>("Quantity added successfully", HttpStatus.OK);
		else
			return new ResponseEntity<>("Something went wrong", HttpStatus.OK);
	}

	@PostMapping("product/update")
	public ResponseEntity<String> updateProduct(@RequestParam("category") String cat,
			@RequestParam("iconFile") List<MultipartFile> iconFile) {
		if (!iconFile.isEmpty()) {
			try {
				Product product = mapper.readValue(cat, Product.class);
				if (product.getProductId() == 0)
					throw new IllegalArgumentException();
				List<String> fileNameList = new ArrayList<>();

				for (MultipartFile file : iconFile) {
					String fileName = file.getOriginalFilename();
					String folderPath = path + "productImage";
					Path filePath = Paths.get(folderPath, fileName);
					Files.write(filePath, file.getBytes());
					fileNameList.add(fileName);
				}
				product.setImages(fileNameList);

				adminService.updateProduct(product);
				return new ResponseEntity<>("Product updated succesfully", HttpStatus.OK);
			} catch (JsonProcessingException exception) {
				return new ResponseEntity<>("provided invalid data", HttpStatus.BAD_REQUEST);

			} catch (IllegalArgumentException exception) {
				return new ResponseEntity<>("provided data is invalid", HttpStatus.BAD_REQUEST);

			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<>("Icon file is empty", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/category/getAllCategoryName")
	public ResponseEntity<List<Object[]>> getAllCategoryName() {
		List<Object[]> list = adminService.getAllCategoryName();
		return new ResponseEntity<List<Object[]>>(list, HttpStatus.OK);
	}

	// Transaction && Order

	@PostMapping("/order/{pageNumber}")
	public ResponseEntity<APIResponse<List<TransactionAdminResponse>>> getAllPayments(@PathVariable String pageNumber) {
		List<Transaction> list = adminService.getAllTransaction(pageNumber);
		List<TransactionAdminResponse> responsesData = new ArrayList<>();
		for (Transaction tr : list) {
			TransactionAdminResponse curr = new TransactionAdminResponse();
			curr.setId(tr.getId());
			curr.setAmount(tr.getAmount());
			curr.setAmount_paid(tr.getAmount_paid());
			curr.setNotes(tr.getNotes());
			curr.setCreated_at(tr.getCreated_at());
			curr.setAmount_due(tr.getAmount_due());
			curr.setCurrency(tr.getCurrency());
			curr.setReceipt(tr.getReceipt());
			curr.setEntity(tr.getEntity());
			curr.setOrder_id(tr.getOrder_id());
			curr.setOffer_id(tr.getOffer_id());
			curr.setStatus(tr.getStatus());
			curr.setAttempts(tr.getAttempts());
			curr.setPaymentId(tr.getPaymentId());
			curr.setUser_id(tr.getUser().getId());
			curr.setUser_phoneNumber(String.valueOf(tr.getUser().getPhoneNumber()));
			curr.setUser_name(tr.getUser().getName());
			curr.setUser_emailId(tr.getUser().getEmailId());
			curr.setUsername(tr.getUser().getUsername());

			responsesData.add(curr);
		}

		return new ResponseEntity<APIResponse<List<TransactionAdminResponse>>>(
				new APIResponse<>("200", responsesData, "OK"), HttpStatus.OK);

	}

	@PostMapping("/order/id/{id}")
	public ResponseEntity<APIResponse<TransactionAdminResponse>> getTransactionById(@PathVariable long id) {

		Transaction tr = adminService.getTransactionById(id);
		TransactionAdminResponse curr = new TransactionAdminResponse();
		curr.setId(tr.getId());
		curr.setAmount(tr.getAmount());
		curr.setAmount_paid(tr.getAmount_paid());
		curr.setNotes(tr.getNotes());
		curr.setCreated_at(tr.getCreated_at());
		curr.setAmount_due(tr.getAmount_due());
		curr.setCurrency(tr.getCurrency());
		curr.setReceipt(tr.getReceipt());
		curr.setEntity(tr.getEntity());
		curr.setOrder_id(tr.getOrder_id());
		curr.setOffer_id(tr.getOffer_id());
		curr.setStatus(tr.getStatus());
		curr.setAttempts(tr.getAttempts());
		curr.setPaymentId(tr.getPaymentId());
		curr.setUser_id(tr.getUser().getId());
		curr.setOrderedProduct(tr.getOrderedProduct().size());
		curr.setUser_phoneNumber(String.valueOf(tr.getUser().getPhoneNumber()));
		curr.setUser_name(tr.getUser().getName());
		curr.setUser_emailId(tr.getUser().getEmailId());
		curr.setUsername(tr.getUser().getUsername());

		return new ResponseEntity<APIResponse<TransactionAdminResponse>>(
				new APIResponse<>("200", curr, "OK"), HttpStatus.OK);

	}

	// feedback
	@PostMapping("/feedback/{pageNumber}")
	public ResponseEntity<APIResponse<List<Feedback>>> getAllFeedback(@PathVariable int pageNumber) {

		List<Feedback> list = adminService.getAllFeedbacks(pageNumber);

		return new ResponseEntity<APIResponse<List<Feedback>>>(
				new APIResponse<>("200", list, "OK"), HttpStatus.OK);

	}

	// help
	@PostMapping("/help/{pageNumber}")
	public ResponseEntity<APIResponse<List<Help>>> getAllHelResponseEntity(@PathVariable int pageNumber) {

		List<Help> list = adminService.getAllHelp(pageNumber);

		return new ResponseEntity<APIResponse<List<Help>>>(
				new APIResponse<>("200", list, "OK"), HttpStatus.OK);

	}

}
