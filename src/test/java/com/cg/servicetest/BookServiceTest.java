package com.cg.servicetest;



import com.cg.dto.BookDTO;
import com.cg.entity.*;
import com.cg.exception.GlobalException;
import com.cg.repository.BookRepository;
import com.cg.repository.InventoryRepository;
import com.cg.service.BookService;

import org.junit.jupiter.api.BeforeEach;
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

    @InjectMocks
    private BookService bookService;

    private Book sampleBook;
    private Author sampleAuthor;
    private Publisher samplePublisher;
    private Category sampleCategory;

    @BeforeEach
    void setUp() {
        // Initialize related entities
        sampleAuthor = new Author();
        sampleAuthor.setAuthorId(1);
        sampleAuthor.setAuthorName("Joshua Bloch");

        samplePublisher = new Publisher();
        samplePublisher.setPublisherId(1);
        samplePublisher.setPublisherName("Addison-Wesley");

        sampleCategory = new Category();
        sampleCategory.setCategoryId(1);
        sampleCategory.setCategoryName("Programming");

        // Initialize the main Book entity
        sampleBook = new Book();
        sampleBook.setBookId(101);
        sampleBook.setTitle("Effective Java");
        sampleBook.setPrice(450.0);
    }

    // -------------------------------------------------------------------------
    // POSITIVE TEST CASES
    // -------------------------------------------------------------------------

    /**
     * 1. Positive: Successfully save a book and verify associations are set correctly.
     */
    @Test
    void testSaveBook_Positive() {
        // Arrange: Mock repository to return the book when saved
        when(bookRepository.save(any(Book.class))).thenReturn(sampleBook);

        // Act: Call service save method
        Book savedBook = bookService.saveBook(sampleBook, sampleAuthor, samplePublisher, sampleCategory);

        // Assert: Check if entities were linked correctly before saving
        assertNotNull(savedBook);
        assertEquals(sampleAuthor, savedBook.getAuthor());
        assertEquals(samplePublisher, savedBook.getPublisher());
        assertEquals(sampleCategory, savedBook.getCategory());
        verify(bookRepository, times(1)).save(sampleBook);
    }

    /**
     * 2. Positive: Successfully find a book by its ID.
     */
    @Test
    void testFindIdByBook_Positive() {
        // Arrange
        when(bookRepository.findById(101)).thenReturn(Optional.of(sampleBook));

        // Act
        Book foundBook = bookService.findIdByBook(101);

        // Assert
        assertNotNull(foundBook);
        assertEquals("Effective Java", foundBook.getTitle());
    }

    /**
     * 3. Positive: Verify the Entity to DTO mapping logic (toDTO).
     */
    @Test
    void testToDTO_Positive() {
        // Arrange: Link entities to the book
        sampleBook.setAuthor(sampleAuthor);
        sampleBook.setCategory(sampleCategory);

        // Act
        BookDTO dto = bookService.toDTO(sampleBook);

        // Assert: Verify mapping of names and IDs
        assertNotNull(dto);
        assertEquals("Effective Java", dto.getTitle());
        assertEquals("Joshua Bloch", dto.getAuthorName());
        assertEquals(1, dto.getCategoryId());
    }

    // -------------------------------------------------------------------------
    // NEGATIVE TEST CASES
    // -------------------------------------------------------------------------

    /**
     * 1. Negative: Handle searching for a non-existent book ID.
     * (Current implementation uses .get() on Optional, which throws NoSuchElementException)
     */
    @Test
    void testFindIdByBook_Negative_NotFound() {
        // Arrange: Return empty Optional
        when(bookRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert: Change the expected class to match your Service
        assertThrows(GlobalException.BookNotFoundException.class, () -> {
            bookService.findIdByBook(999);
        });
    }


    /**
     * 2. Negative: Test toDTOList with a null input.
     * Service should return an empty list instead of throwing NullPointerException.
     */
    @Test
    void testToDTOList_Negative_NullInput() {
        // Act
        List<BookDTO> result = bookService.toDTOList(null);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty(), "Result should be an empty list for null input");
    }

    /**
     * 3. Negative: Test toDTO when related entities (Author/Publisher) are missing.
     * Logic should not crash with NullPointerException.
     */
    @Test
    void testToDTO_Negative_MissingRelations() {
        // Arrange: Book has no Author or Publisher set (they are null)
        Book bookWithNoRefs = new Book();
        bookWithNoRefs.setTitle("Mystery Book");

        // Act
        BookDTO resultDto = bookService.toDTO(bookWithNoRefs);

        // Assert: Names should be null but object mapping should complete
        assertNotNull(resultDto);
        assertNull(resultDto.getAuthorName());
        assertNull(resultDto.getPublisherName());
    }
}
