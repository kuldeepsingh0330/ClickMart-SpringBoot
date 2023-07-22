package com.ransankul.clickmart.controller;

import com.ransankul.clickmart.model.APIResponse;
import com.ransankul.clickmart.model.OrderRequest;
import com.ransankul.clickmart.model.Product;
import com.ransankul.clickmart.model.Transaction;
import com.ransankul.clickmart.model.User;
import com.ransankul.clickmart.payloads.TransactionResponse;
import com.ransankul.clickmart.security.JWTTokenHelper;
import com.ransankul.clickmart.service.ProductService;
import com.ransankul.clickmart.service.TransactionService;
import com.ransankul.clickmart.service.UserService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import jakarta.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Value("${razorpay.keyId}")
    private String keyId;

    @Value("${razorpay.keySecret}")
    private String keySecret;
    
    @Autowired
    private UserService userService;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;
    
    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private ProductService productService;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest data,HttpServletRequest request) throws Exception{

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String userName = jwtTokenHelper.extractUsername(token);
        	User user = userService.finduserByUsername(userName);
        	double prices = 0;
        	ArrayList<Product> productList = new ArrayList<>();        	
        	
        	for(int i = 0;i<data.getProductList().size();i++) {
        		int productId = data.getProductList().get(i);
        		Product pro = productService.getProductById(productId);
        		
        		productList.add(pro);
        		int quantity = data.getQuantityList().get(i);
        		
        		double pri = pro.getPrice()*quantity;
        		double dis = pro.getDiscount()*quantity;
        		
        		prices = prices+(pri-dis);
        	}
        	
        	int price = (int)Math.round(prices);
        	System.out.println(price);
        	System.out.println(prices);
            
        	RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);
        	JSONObject obj = new JSONObject();
        	obj.put("amount", price*100);
        	obj.put("currency", "INR");
        	obj.put("receipt", "CM-0330");
        	
        	Order order = razorpayClient.orders.create(obj);
        	String notes, offerId;
        	try {
        		notes = order.get("notes").toString();
        		offerId = order.get("offer_id").toString();
        	}catch (Exception e) {
        		notes = "";
        		offerId = "";
    		}
        	
        	//code to save in localdatabase
        	Transaction tr = new Transaction(order.get("amount"),
        			order.get("amount_paid"),
        			notes,
        			order.get("created_at"),
        			order.get("amount_due"),
        			order.get("currency"),
        			order.get("receipt"),
        			order.get("id"),
        			order.get("entity"),
        			offerId,
        			order.get("status"),
        			order.get("attempts"));
        	
        	tr.setUser(user);
        	tr.setOrderedProduct(productList);
        	tr.setAddress(data.getDeliveryAddress().getAddressId());
        	transactionService.saveTransaction(tr);
        	
        	
            return new ResponseEntity<>(order.toString() ,HttpStatus.OK);        	
            
        }else {
        	return new ResponseEntity<>(null ,HttpStatus.BAD_REQUEST);        	
        }
        
    }

    @PostMapping("/verify-payment")
    public ResponseEntity<APIResponse<String>> verifyPayment(@RequestBody Map<String, String> data) {

    	String orderId = data.get("razorpay_order_id");
        String paymentId = data.get("razorpay_payment_id");
        String status = data.get("status");
        
        

        Transaction tr = transactionService.getTransactionByTransactionId(orderId);
        
        if(status == "paid") {
            tr.setPaymentId(paymentId);
            tr.setAmount_due(0);
            tr.setAmount_paid(tr.getAmount());
            tr.setStatus(status);
            transactionService.saveTransaction(tr);  
        }else {
            tr.setStatus(status);
            transactionService.saveTransaction(tr);  
        }
       
        
        return new ResponseEntity<>(new APIResponse<>("200","","updated succesfully"),HttpStatus.OK);

    }
    
    @PostMapping("/{pageNumber}")
    public ResponseEntity<APIResponse<List<TransactionResponse>>> getAllPayments(@PathVariable String pageNumber,HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String userName = jwtTokenHelper.extractUsername(token);
        	User user = userService.finduserByUsername(userName);
        	List<Transaction> list = transactionService.getAllTransactionByUser(user,pageNumber);
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
        	
        }else {
        	return new ResponseEntity<>(null ,HttpStatus.BAD_REQUEST); 
        }
        
    }
}

