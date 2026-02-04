package com.cg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cg.dto.BookDTO;
import com.cg.entity.Author;
import com.cg.entity.Book;
import com.cg.entity.Category;
import com.cg.entity.Publisher;
import com.cg.service.AuthorService;
import com.cg.service.BookService;
import com.cg.service.CategoryService;
import com.cg.service.PublisherService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookService bookService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private PublisherService publisherService;
	
	@GetMapping("/list")
	public String getAllBooks(Model model) {
		List<Book> books = bookService.getAllBooks();
		
		 model.addAttribute("booksDTO",books);
		 return "book/books-list";
	}
	
	@GetMapping("/add")
	public String addBook(Model model) {
		model.addAttribute("bookDTO",new BookDTO());
		model.addAttribute("categories",categoryService.getAllCategories());
		model.addAttribute("authors",authorService.getAllAuthors());
		model.addAttribute("publishers",publisherService.getAllPublishers());		
		return "book/book-add";
	}
	
	@PostMapping("/save")
	public String saveBook(@Valid @ModelAttribute("bookDTO") BookDTO bookDTO) {
          
		Author author = authorService.getAuthorById(bookDTO.getAuthorId());
		Publisher publisher = publisherService.findById(bookDTO.getPublisherId());
		Category category =  categoryService.getCategoryById(bookDTO.getCategoryId());
		Book book = bookService.toEntity(bookDTO);
		bookService.saveBook(book,author,publisher,category);
		return "redirect:/books/list";
	}

}
