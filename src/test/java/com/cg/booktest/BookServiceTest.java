package com.cg.booktest;

import com.cg.dto.CartItem;
import com.cg.entity.*;
import com.cg.repository.BookRepository;
import com.cg.repository.InventoryRepository;
import com.cg.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void saveBook_ShouldSetAssociationsAndSave() {
        // Arrange
        Book book = new Book();
        Author author = new Author();
        Publisher publisher = new Publisher();
        Category category = new Category();

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Act
        Book result = bookService.saveBook(book, author, publisher, category);

        // Assert
        assertEquals(author, result.getAuthor());
        assertEquals(publisher, result.getPublisher());
        assertEquals(category, result.getCategory());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void findIdByBook_ShouldReturnBook_WhenFound() {
        // Arrange
        Book book = new Book();
        book.setBookId(101);
        when(bookRepository.findById(101)).thenReturn(Optional.of(book));

        // Act
        Book result = bookService.findIdByBook(101);

        // Assert
        assertNotNull(result);
        assertEquals(101, result.getBookId());
    }

    @Test
    void processBooking_ShouldReduceQuantityAndSaveInventory() {
        // Arrange
        CartItem item = new CartItem();
        item.setBookId(1);
        List<CartItem> cart = List.of(item);

        Inventory inventory = new Inventory();
        inventory.setQuantity(5);
        
        when(inventoryRepository.findByBook_BookId(1)).thenReturn(Optional.of(inventory));

        // Act
        bookService.processBooking(cart);

        // Assert
        assertEquals(4, inventory.getQuantity());
        verify(inventoryRepository, times(1)).save(inventory);
    }


}
