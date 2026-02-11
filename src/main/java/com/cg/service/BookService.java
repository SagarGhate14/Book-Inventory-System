package com.cg.service;

import java.util.ArrayList;




import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dto.BookDTO;
import com.cg.entity.Author;
import com.cg.entity.Book;
import com.cg.entity.Category;
import com.cg.entity.Publisher;
import com.cg.exception.BookNotFoundException;
import com.cg.exception.GlobalException;
import com.cg.repository.BookRepository;



@Service
public class BookService implements IBookService{

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	
	
	//Get all the books from db
	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	
   //Save the new Book in db
	@Override
	public Book saveBook(Book book,Author author,Publisher publisher,Category category) {
		 book.setAuthor(author);
		 book.setPublisher(publisher);
		 book.setCategory(category);
		return bookRepository.save(book);
	}
 
	//Delete the book by Id
	@Override
	public void deleteBook(int bId) {
           bookRepository.deleteById(bId);		
	}
	
	//Find the book by Id
	@Override
	public Book findIdByBook(int bId) {
	    return bookRepository.findById(bId)
	            .orElseThrow(() -> new BookNotFoundException(bId));
	}

	//Update the book 
	@Override
	public Book updateBook(Book book,Author author,Publisher publisher,Category category) {
		book.setAuthor(author);
		book.setPublisher(publisher);
		book.setCategory(category);
		return bookRepository.save(book);
	}
	
	
	//Converting the DTO to Entity
	
	public Book toEntity(BookDTO dto) {
	    if (dto == null) {
	        return null;
	    }
	    
	    Book entity = new Book();
	    entity.setBookId(dto.getBookId());
	    entity.setTitle(dto.getTitle());
	    entity.setPrice(dto.getPrice());
    
	    return entity;
	}
	 
	//Converting entityList to DTOList
	
	public List<BookDTO> toDTOList(List<Book> books) {
	    List<BookDTO> dtoList = new ArrayList<>();
	    
	    if (books == null) {
	        return dtoList;
	    }

	    for (Book book : books) {
	        BookDTO dto = toDTO(book);
	        dtoList.add(dto);
	    }
	    
	    return dtoList;
	    }
	
	
	// Converting entity to DTO
	
	public BookDTO toDTO(Book book) {
	    BookDTO dto = new BookDTO();
	    dto.setBookId(book.getBookId());
	    dto.setTitle(book.getTitle());
	    dto.setPrice(book.getPrice());
	    dto.setInventory(book.getInventory());
	 
	    if (book.getAuthor() != null) {
	        dto.setAuthorId(book.getAuthor().getAuthorId());
	        dto.setAuthorName(book.getAuthor().getAuthorName());
	    }
	    if (book.getPublisher() != null) {
	        dto.setPublisherId(book.getPublisher().getPublisherId());
	        dto.setPublisherName(book.getPublisher().getPublisherName());
	    }
	    if (book.getCategory() != null) {
	        dto.setCategoryId(book.getCategory().getCategoryId());
	        dto.setCategoryName(book.getCategory().getCategoryName());
	    }
	    
	    return dto;
	}
	


}
