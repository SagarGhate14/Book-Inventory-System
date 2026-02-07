package com.cg.booktest;

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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
class BookControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private BookService bookService;
    @MockBean private CategoryService categoryService;
    @MockBean private AuthorService authorService;
    @MockBean private PublisherService publisherService;

  
    @Test
    void testSaveBook_Success() throws Exception {
        // 1. Arrange: Setup DTO with valid data
        BookDTO dto = new BookDTO();
        dto.setAuthorId(1); 
        dto.setPublisherId(1); 
        dto.setCategoryId(1);
        dto.setTitle("Test Book");

        // 2. Stub all mandatory service calls to prevent NullPointerExceptions
        when(authorService.getAuthorById(anyInt())).thenReturn(new Author());
        when(publisherService.findById(anyInt())).thenReturn(new Publisher());
        when(categoryService.getCategoryById(anyInt())).thenReturn(new Category());
        
        Book mockBook = new Book();
        when(bookService.toEntity(any(BookDTO.class))).thenReturn(mockBook);
        
        // IMPORTANT: Stub the saveBook method itself to return the mock book
        when(bookService.saveBook(any(), any(), any(), any())).thenReturn(mockBook);

        // 3. Act & Assert
        mockMvc.perform(post("/books/save")
                        .flashAttr("bookDTO", dto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/list"));

        // 4. Verify the service was actually called
        verify(bookService, times(1)).saveBook(any(), any(), any(), any());
    }

    @Test
    void testDeleteBook_Success() throws Exception {
        mockMvc.perform(get("/books/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/list"));

        verify(bookService).deleteBook(1);
    }
}
