package com.ransankul.clickmart.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN_USER')")
public class AdminFrontController {

    @RequestMapping("/home")
	public String home(Model m){
		m.addAttribute("title","Home - ClickMart");
		return "home";
	}

	@RequestMapping("/category")
	public String category(Model m){
		m.addAttribute("totalCategory","50");
		m.addAttribute("title","Categoey - ClickMart");
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
	
    
}
