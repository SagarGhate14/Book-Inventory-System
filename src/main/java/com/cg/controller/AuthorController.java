package com.cg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("author", new Author());
        return "author/author-add";
    }

    @PostMapping("/add")
    public String addAuthor(@ModelAttribute Author author) {
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
    public String updateAuthor(@PathVariable("id") int id, Model model) {
        Author author = authorService.getAuthorById(id);
        AuthorDTO authorDTO = authorService.toDTO(author);
        if (authorDTO == null) {
            return "redirect:/authors/list";
        }
        model.addAttribute("authorDTO", authorDTO);
        return "author/author-edit";
    }

    @PostMapping("update")
    public String updatedAuthor(@ModelAttribute("authorDTO") AuthorDTO authordto) {
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