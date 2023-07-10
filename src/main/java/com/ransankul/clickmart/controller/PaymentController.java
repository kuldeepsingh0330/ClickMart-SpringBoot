package com.ransankul.clickmart.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.RazorpaySignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Value("${razorpay.keyId}")
    private String keyId;

    @Value("${razorpay.keySecret}")
    private String keySecret;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder() {
        try {
            RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);
            Map<String, Object> params = new HashMap<>();
            params.put("amount", 1000); // Amount in paise (100 paise = 1 INR)
            params.put("currency", "INR");
            params.put("receipt", "order_rcptid_" + System.currentTimeMillis());
            Order order = razorpayClient.Orders.create(params);
            return ResponseEntity.ok(order);
        } catch (RazorpayException e) {
            // Handle exception
            return ResponseEntity.status(500).body("Failed to create order.");
        }
    }

    @PostMapping("/verify-payment")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> data) {
        try {
            String orderId = data.get("razorpay_order_id");
            String paymentId = data.get("razorpay_payment_id");
            String signature = data.get("razorpay_signature");

            boolean isValidSignature = RazorpaySignature.verifyPaymentSignature(data, keySecret, signature);
            if (isValidSignature) {
                // Payment verification succeeded
                return ResponseEntity.ok("Payment verified successfully.");
            } else {
                // Payment verification failed
                return ResponseEntity.badRequest().body("Payment verification failed.");
            }
        } catch (RazorpayException e) {
            // Handle exception
            return ResponseEntity.status(500).body("Failed to verify payment.");
        }
    }
}

