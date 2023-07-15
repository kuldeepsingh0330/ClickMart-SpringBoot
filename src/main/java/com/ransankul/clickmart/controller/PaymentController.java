package com.ransankul.clickmart.controller;

import com.ransankul.clickmart.model.Transaction;
import com.ransankul.clickmart.service.TransactionService;
import com.razorpay.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Value("${razorpay.keyId}")
    private String keyId;

    @Value("${razorpay.keySecret}")
    private String keySecret;
    
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Map<String,Object> data) throws Exception{

        int price = (int) Math.floor(Float.parseFloat(data.get("amount").toString()));
        
    	RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);
    	JSONObject obj = new JSONObject();
    	obj.put("amount", price*100);
    	obj.put("currency", "INR");
    	obj.put("receipt", "CM-0330");
    	
    	Order order = razorpayClient.orders.create(obj);
    	System.out.println(order);
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
    	transactionService.saveTransaction(tr);
    	
    	
    	
        return ResponseEntity.ok(order.toString());
    }

//    @PostMapping("/verify-payment")
//    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> data) {
//        try {
//            String orderId = data.get("razorpay_order_id");
//            String paymentId = data.get("razorpay_payment_id");
//            String signature = data.get("razorpay_signature");
//
//            boolean isValidSignature = RazorpaySignature.verifyPaymentSignature(data, keySecret, signature);
//            if (isValidSignature) {
//                // Payment verification succeeded
//                return ResponseEntity.ok("Payment verified successfully.");
//            } else {
//                // Payment verification failed
//                return ResponseEntity.badRequest().body("Payment verification failed.");
//            }
//        } catch (RazorpayException e) {
//            // Handle exception
//            return ResponseEntity.status(500).body("Failed to verify payment.");
//        }
//    }
}

