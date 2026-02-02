package com.cg.service;

import
org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.Author;
import com.cg.repository.AuthorRepository;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public void saveAuthor (Author author) {
        authorRepository.save(author);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(int id) {
        return authorRepository. findById(id).orElse(null);
    }

    public void deleteAuthor(int id) {
        authorRepository.deleteById(id);
    }
}