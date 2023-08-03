package com.ransankul.clickmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ransankul.clickmart.service.AdminService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")				
public class AdminFrontController {

    @Autowired
	private AdminService adminService;

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
    
}
