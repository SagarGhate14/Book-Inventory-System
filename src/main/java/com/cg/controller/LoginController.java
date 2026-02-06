package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cg.entity.User;
import com.cg.service.CustomerUserDetailService;
import com.cg.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@Autowired
	private CustomerUserDetailService userService;
	
	    @GetMapping("/login")
	    public String login() {
	        return "user/login"; // Refers to login.html
	    }
	    
	    @PostMapping("/login")
	    public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
	        User user = userService.login(email, password);
	        if (user != null) {
	            // Store the role in the session so it survives a refresh
	        	 session.setAttribute("loggedInUser", user);
	            session.setAttribute("userRole", user.getRole().toString()); 
	            return "redirect:/dashboard";
	        }
	        return "login";
	    }


}
