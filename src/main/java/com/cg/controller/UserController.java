package com.cg.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cg.dto.UserDTO;

import com.cg.entity.User;

import com.cg.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Value("${app.security.corp-id}")
    private String corpId;

    // LIST USERS
    @GetMapping("/list")
    public String getAllUsers(Model model) {
    	List<User> users = userService.getAllUsers();
    	List<UserDTO> usersDTO = userService.toDTOList(users);
        model.addAttribute("usersDTO", usersDTO);
        return "user/user-list"; 
    }
    
    //CORP_ID page 
    @GetMapping("/newAdmin")
    public String newAdmin() {
    	return "user/admin-corp";
    }
    
    //Checks the CORP_ID
    @PostMapping("/verify-corp")
    public String verify(@RequestParam("corpId") String inputCorpId, RedirectAttributes redirectAttributes) {
    	 if(corpId.equals(inputCorpId)) {
    		 return "redirect:/users/add";
    		 
    	 }else {
    		  redirectAttributes.addFlashAttribute("error", "Invalid Corporate ID. Please try again.");
    		  return "redirect:/users/newAdmin";
    	 }
    }
    
    //Admin add page
    @GetMapping("/add")
    public String addAdmin(Model model) {
    	model.addAttribute("userDTO",new UserDTO());
    	return "user/admin-add";
    }

    // Staff add page
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "user/user-add";
    }

    // SAVE Staff
    @PostMapping("/add")
    public String saveUser(@Valid @ModelAttribute("userDTO") UserDTO userDTO,BindingResult result) {
    	if (result.hasErrors()) {
	        // 2. If errors exist, stop and return the EDIT form
	        return "user/user-add"; 
	    }
    	
      User user = userService.toEntity(userDTO);
      userService.saveUser(user);
        return "redirect:/users/list";
    }
    
    //Save Admin
    @PostMapping("/addAdmin")
    public String saveAdmin(@Valid @ModelAttribute("userDTO") UserDTO userDTO,BindingResult result) {
    	if (result.hasErrors()) {
	        // 2. If errors exist, stop and return the EDIT form
	        return "user/admin-add"; 
	    }
    	
      User user = userService.toEntity(userDTO);
      userService.saveUser(user);
        return "redirect:/users/list";
    }
    
    
    // DELETE USER
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/users/list";
    }
}