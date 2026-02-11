package com.cg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class LoginController {

	     //Login page
	    @GetMapping("/login")
	    public String login() {
	        return "user/login"; // Refers to login.html
	    }
	    
	     
	    @GetMapping("/403")
	    public String accessDenied() {
	        // This manually throws the exception so your GlobalException.java picks it up
	        throw new org.springframework.security.access.AccessDeniedException("You do not have permission to access this page.");
	    }

}
