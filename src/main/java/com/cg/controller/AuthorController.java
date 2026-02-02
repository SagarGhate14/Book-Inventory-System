package com.cg.controller;

import
org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import
org.springframework.ui.Model;
import
org.springframework.web.bind.annotation.GetMapping;
import
org.springframework.web.bind.annotation.PathVariable;
import
org.springframework.web.bind.annotation.PostMapping;
import
org.springframework.web.bind.annotation.RequestMapping;

import com.cg.entity.Author;
import com.cg.service.AuthorService;

@Controller
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping("/add")
    public String addAuthor(Author author) {
        authorService.saveAuthor(author);
        return "author-success";
    }

    @GetMapping("/list")
    public String listAuthors(Model model) {
        model.addAttribute("authors",
        authorService.getAllAuthors());
        return "author-list";
    }

    @GetMapping("/view/{id}")
    public String viewAuthor(@PathVariable int id, Model model) {
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "author-view";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable int id) {
        authorService.deleteAuthor(id);
        return "redirect:/author/list";
    }
}