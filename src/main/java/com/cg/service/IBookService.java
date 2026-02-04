package com.cg.service;

import java.util.List;

import com.cg.entity.Author;
import com.cg.entity.Book;
import com.cg.entity.Category;
import com.cg.entity.Publisher;

public interface IBookService {
	public List<Book> getAllBooks();
	public Book findIdByBook(int bId);
	public Book saveBook(Book book,Author author,Publisher publisher,Category category);
	public void deleteBook(Book book);

}
