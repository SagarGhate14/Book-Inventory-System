package com.cg.service;

import java.util.List;

import com.cg.entity.Author;

public interface IAuthorService {
	
	 public void saveAuthor(Author author);
	 public List<Author> getAllAuthors();
	 public Author getAuthorById(int id);
	 public void deleteAuthor(int id);
	 public Author updateAuthor(Author author);
	 

}
