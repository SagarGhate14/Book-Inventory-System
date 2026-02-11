package com.cg.controllertest;

import com.cg.controller.BookController;
import com.cg.dto.BookDTO;
import com.cg.entity.*;
import com.cg.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
class BookControllerTest {

	@Autowired
    private MockMvc mockMvc;

    // We must mock ALL services injected in BookController
    @MockBean
    private BookService bookService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private PublisherService publisherService;

    // -------------------------------------------------------------------------
    // POSITIVE TEST CASES
    // -------------------------------------------------------------------------

    /**
     * 1. Positive: Successfully retrieve the list of all books.
     */
    @Test
    void testGetAllBooks_Positive() throws Exception {
        // Arrange
        when(bookService.getAllBooks()).thenReturn(new ArrayList<>());
        when(bookService.toDTOList(any())).thenReturn(new ArrayList<>());

        // Act & Assert
        mockMvc.perform(get("/books/list")
                .with(csrf())) // <--- ADD THIS LINE TO FIX THE ERROR
                .andExpect(status().isOk())
                .andExpect(view().name("book/books-list"))
                .andExpect(model().attributeExists("booksDTO"));
    }

    /**
     * 2. Positive: Successfully save a book when all fields are valid.
     */
    @Test
    void testSaveBook_Positive() throws Exception {
        // Act: Perform POST with all required valid parameters
        mockMvc.perform(post("/books/save")
                .param("title", "Spring Boot Guide") // Valid title (>2 chars)
                .param("price", "550.00")            // Valid price (>= 0.0)
                .param("authorId", "1")              // Valid ID selection
                .param("publisherId", "1")
                .param("categoryId", "1"))
                .andExpect(status().is3xxRedirection()) // Expect Redirect (302)
                .andExpect(redirectedUrl("/books/list")); // Verify redirect path

        // Assert: Verify the save service method was actually called
        verify(bookService, times(1)).saveBook(any(), any(), any(), any());
    }

    /**
     * 3. Positive: Successfully delete a book by ID.
     */
    @Test
    void testDeleteBook_Positive() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/books/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/list"));

        // Verify service logic was triggered
        verify(bookService, times(1)).deleteBook(1);
    }

    // -------------------------------------------------------------------------
    // NEGATIVE TEST CASES (Validation Failures)
    // -------------------------------------------------------------------------

    /**
     * 1. Negative: Fail to save because title is blank (triggers @NotBlank).
     */
    @Test
    void testSaveBook_Negative_BlankTitle() throws Exception {
        // Act: Send empty title
        mockMvc.perform(post("/books/save")
                .param("title", "") 
                .param("price", "100.0")
                .param("authorId", "1")
                .param("publisherId", "1")
                .param("categoryId", "1"))
                .andExpect(status().isOk()) // Returns to the form (200 OK)
                .andExpect(view().name("book/book-add")) // Stays on the "add" page
                .andExpect(model().attributeHasFieldErrors("bookDTO", "title")); // Verify specific error
    }

    /**
     * 2. Negative: Fail to save because price is negative (triggers @DecimalMin).
     */
    @Test
    void testSaveBook_Negative_InvalidPrice() throws Exception {
        // Act: Send negative price
        mockMvc.perform(post("/books/save")
                .param("title", "Valid Title")
                .param("price", "-50.0") 
                .param("authorId", "1")
                .param("publisherId", "1")
                .param("categoryId", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("bookDTO", "price"));
        
        // Ensure the service save method was NEVER called due to validation error
        verify(bookService, never()).saveBook(any(), any(), any(), any());
    }

    /**
     * 3. Negative: Fail to save because required IDs are missing (triggers @NotNull).
     */
    @Test
    void testSaveBook_Negative_MissingIds() throws Exception {
        // Act: Send title and price but omit dropdown IDs (author, category, publisher)
        mockMvc.perform(post("/books/save")
                .param("title", "Java Persistence")
                .param("price", "75.0"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("bookDTO", "authorId"))
                .andExpect(model().attributeHasFieldErrors("bookDTO", "publisherId"))
                .andExpect(model().attributeHasFieldErrors("bookDTO", "categoryId"));
        
        // Note: Your controller re-adds lists to the model on error; we could verify that too:
        verify(categoryService, atLeastOnce()).getAllCategories();
    }
}
