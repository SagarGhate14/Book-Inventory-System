package com.cg.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cg.dto.AuthorDTO;
import com.cg.entity.Author;
import com.cg.service.AuthorService;



@Controller
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    AuthorService authorService;
    
    @GetMapping("/new")
    public String addAuthor(Model model) {
        model.addAttribute("author",new Author());
        return "author/author-add";
    }

    @PostMapping("/add")
    public String addAuthor(Author author) {
        authorService.saveAuthor(author);
        return "redirect:/authors/list";
    }

    @GetMapping("/list")
    public String listAuthors(Model model) {
    	List<Author> authors = authorService.getAllAuthors();
    	List<AuthorDTO> authorsDTO = authorService.toDTOList(authors);
        model.addAttribute("authors",authorsDTO);
        return "author/author-list";
    }

    @GetMapping("/view/{id}")
    public String viewAuthor(@PathVariable int id, Model model) {
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "author-view";
    }
    
    @GetMapping("/edit/{id}")
    public String updateAuthor(@PathVariable int id,Model model) {
        Author author = authorService.getAuthorById(id);
        AuthorDTO authorDTO = authorService.toDTO(author);
        model.addAttribute("authorDTO",authorDTO);
        return "author-edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable int id) {
        authorService.deleteAuthor(id);
        return "redirect:/author/list";
    }
}