package com.cg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cg.dto.UserDTO;
import com.cg.entity.Role;
import com.cg.service.IUserService;

@Controller
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    // LIST USERS
    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users"; // ✅ users.html (no .html)
    }

    // OPEN ADD FORM
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles", Role.values());
        return "user-form";
    }

    // SAVE USER
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") UserDTO userDTO) {
        userService.saveUser(userDTO);
        return "redirect:/users";
    }

    // OPEN EDIT FORM
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", Role.values());
        return "user-form";
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