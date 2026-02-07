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
import com.cg.entity.User;
import com.cg.repository.BookRepository;
import com.cg.repository.InventoryRepository;

import com.cg.repository.UserRepository;


@Service
public class BookService implements IBookService{

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private InventoryRepository inventoryRepository;
	@Autowired
	private UserRepository userRepository;
	
	
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
		 // 1. Find the User who is logged in
      
        
        // 3. Update Inventory for each book in the cart
        for (CartItem item : cart) {
            // Find inventory record for this specific book
            Inventory inventory = inventoryRepository.findByBook_BookId(item.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found in inventory"));

            // Check if we have enough stock
            if (inventory.getQuantity() < 1) {
                throw new RuntimeException("Book " + item.getTitle() + " just went out of stock!");
            }

            // Reduce quantity by 1
            inventory.setQuantity(inventory.getQuantity() - 1);
            
            // Auto-update status if it hits zero
            if (inventory.getQuantity() == 0) {
                inventory.setStatus("OUT_OF_STOCK");
            }

            // Save the updated inventory stock
            inventoryRepository.save(inventory);
        }

        
       
	}
	
	

}
