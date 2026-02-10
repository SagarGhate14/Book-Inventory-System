package com.cg.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cg.dto.AuthorDTO;
import com.cg.entity.Author;
import com.cg.service.AuthorService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping("/new")
    public String addAuthor(Model model) {
        model.addAttribute("author", new AuthorDTO());
        return "author/author-add";
    }

    @PostMapping("/add")
    public String addAuthor(@Valid @ModelAttribute("author") AuthorDTO authorDTO,BindingResult result) {
    	if (result.hasErrors()) {
	        // 2. If errors exist, stop and return the EDIT form
	        return "author/author-add"; 
	    }
    	Author author = authorService.toEntity(authorDTO);
        authorService.saveAuthor(author);
        return "redirect:/authors/list";
    }
    
    @GetMapping("/list")
    public String listAuthors(Model model) {
        List<Author> authors = authorService.getAllAuthors();
        List<AuthorDTO> authorsDTO = authorService.toDTOList(authors);
        model.addAttribute("authors", authorsDTO);
        return "author/author-list";
    }
    
    @GetMapping("/edit/{id}")
    public String updateAuthor(@PathVariable("id") int id,BindingResult result, Model model) {
    	if (result.hasErrors()) {
            // DO NOT redirect. Return the view directly to keep the errors.
            return "inventory/inventory-edit"; 
        }
    	
        Author author = authorService.getAuthorById(id);
        AuthorDTO authorDTO = authorService.toDTO(author);
        if (authorDTO == null) {
            return "redirect:/authors/list";
        }
        model.addAttribute("authorDTO", authorDTO);
        return "author/author-edit";
    }
    
    @PostMapping("update")
    public String updatedAuthor(@Valid @ModelAttribute("authorDTO") AuthorDTO authordto,BindingResult result) {
    	
    	 if (result.hasErrors()) {
    	        // 2. If errors exist, stop and return the EDIT form
    	        return "author/author-edit"; 
    	    }
        Author author = authorService.toEntity(authordto);
        authorService.updateAuthor(author);
        return "redirect:/authors/list";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable int id) {
        authorService.deleteAuthor(id);
        return "redirect:/authors/list";
    }
}