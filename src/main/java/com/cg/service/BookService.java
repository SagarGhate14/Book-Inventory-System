package com.cg.service;

import java.util.ArrayList;


import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dto.BookDTO;
import com.cg.dto.CartItem;
import com.cg.entity.Author;
import com.cg.entity.Book;
import com.cg.entity.Category;
import com.cg.entity.Inventory;
import com.cg.entity.Publisher;
import com.cg.repository.BookRepository;
import com.cg.repository.InventoryRepository;


@Service
public class BookService implements IBookService{

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	

	@Override
	public Book saveBook(Book book,Author author,Publisher publisher,Category category) {
		 book.setAuthor(author);
		 book.setPublisher(publisher);
		 book.setCategory(category);
		return bookRepository.save(book);
	}

	@Override
	public void deleteBook(int bId) {
           bookRepository.deleteById(bId);		
	}
	@Override
	public Book findIdByBook(int bId) {
		// TODO Auto-generated method stub
		return bookRepository.findById(bId).get();
	}



	@Override
	public Book updateBook(Book book,Author author,Publisher publisher,Category category) {
		book.setAuthor(author);
		book.setPublisher(publisher);
		book.setCategory(category);
		return bookRepository.save(book);
	}
	
	
	
	public Book toEntity(BookDTO dto) {
	    if (dto == null) {
	        return null;
	    }
	    
	    Book entity = new Book();
	    entity.setBookId(dto.getBookId());
	    entity.setTitle(dto.getTitle());
	    entity.setPrice(dto.getPrice());
	    
	    // Note: Author, Publisher, and Category entities are usually 
	    // fetched from DB and set in the Service layer based on the IDs.
	    
	    return entity;
	}
	 
	 
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
	
	
	// Inside your BookService.java
	public BookDTO toDTO(Book book) {
	    BookDTO dto = new BookDTO();
	    dto.setBookId(book.getBookId());
	    dto.setTitle(book.getTitle());
	    dto.setPrice(book.getPrice());
	    
	    // Ensure these match the NEW capitalized names from Step 1
	    if (book.getAuthor() != null) {
	        dto.setAuthorId(book.getAuthor().getAuthorId());
	    }
	    if (book.getPublisher() != null) {
	        dto.setPublisherId(book.getPublisher().getPublisherId());
	    }
	    if (book.getCategory() != null) {
	        dto.setCategoryId(book.getCategory().getCategoryId());
	    }
	    
	    return dto;
	}
	
	
	
	@Transactional // Ensures the database stays consistent
    public void processBooking(List<CartItem> cart) {
		 // If your CartItem already has a 'quantity' field, use item.getQuantity()
	    // Otherwise, we count occurrences in the list
	    Map<Integer, Long> bookCounts = cart.stream()
	            .collect(Collectors.groupingBy(CartItem::getBookId, Collectors.counting()));

	    for (Map.Entry<Integer, Long> entry : bookCounts.entrySet()) {
	        Integer bookId = entry.getKey();
	        int totalOrdered = entry.getValue().intValue();

	        // 2. Fetch inventory for this specific book
	        Inventory inventory = inventoryRepository.findByBook_BookId(bookId)
	            .orElseThrow(() -> new RuntimeException("Inventory record missing for Book ID: " + bookId));

	        int currentStock = inventory.getQuantity();

	        // 3. Validation: check if stock can handle the total order
	        if (currentStock < totalOrdered) {
	            throw new RuntimeException("Insufficient stock! Available: " + currentStock + ", Requested: " + totalOrdered);
	        }

	        // 4. Subtract and Save
	        inventory.setQuantity(currentStock - totalOrdered);
	        inventoryRepository.saveAndFlush(inventory);
	    }
	}

}
