package com.cg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cg.dto.UserDTO;
import com.cg.entity.Role;
import com.cg.entity.User;
import com.cg.service.IUserService;
import com.cg.service.UserService;

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
    @GetMapping("/newAdmin")
    public String newAdmin() {
    	return "user/admin-corp";
    }
    
    @PostMapping("/verify-corp")
    public String verify(@RequestParam("corpId") String inputCorpId, RedirectAttributes redirectAttributes) {
    	 if(corpId.equals(inputCorpId)) {
    		 return "redirect:/users/add";
    		 
    	 }else {
    		  redirectAttributes.addFlashAttribute("error", "Invalid Corporate ID. Please try again.");
    		  return "redirect:/users/newAdmin";
    	 }
    }
    
    @GetMapping("/add")
    public String addAdmin(Model model) {
    	model.addAttribute("userDTO",new UserDTO());
    	return "user/admin-add";
    }

    // OPEN ADD FORM
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "user/user-add";
    }

    // SAVE USER
    @PostMapping("/add")
    public String saveUser(@ModelAttribute("userDTO") UserDTO userDTO) {
      User user = userService.toEntity(userDTO);
      userService.saveUser(user);
        return "redirect:/users/list";
    }

    // OPEN EDIT FORM
    @GetMapping("/update/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
    	User user = userService.getUserById(id);
        model.addAttribute("user", userService.getUserById(id));
        return "user/user-edit";
    }

    // UPDATE USER
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable int id, @ModelAttribute("user") UserDTO userDTO) {
        userService.updateUser(id, userDTO);
        return "redirect:/users";
    }

    // DELETE USER
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}