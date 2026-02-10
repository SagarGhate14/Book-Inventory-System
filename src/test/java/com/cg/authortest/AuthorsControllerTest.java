package com.cg.authortest;

import com.cg.controller.AuthorController;
import com.cg.dto.AuthorDTO;
import com.cg.entity.Author;
import com.cg.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthorController.class)
@AutoConfigureMockMvc(addFilters = false) // disable security filters if Spring Security is present
public class AuthorsControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    // POSITIVE TEST CASES  
    
     // 1. Positive: Successfully add a new author with valid data.
     
    @Test
    void testAddAuthor_Positive() throws Exception {
        mockMvc.perform(post("/authors/add")
                .with(csrf()) // Prevents 403 Forbidden
                .param("authorName", "J.K. Rowling")
                .param("authorEmail", "jk@example.com")
                .param("authorCountry", "UK"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/authors/list"));

        verify(authorService, times(1)).saveAuthor(any());
    }

    
     // 2. Positive: Successfully delete an author.
     
    @Test
    void testDeleteAuthor_Positive() throws Exception {
        mockMvc.perform(get("/authors/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/authors/list"));

        verify(authorService).deleteAuthor(1);
    }

    // NEGATIVE TEST CASES

    
     // 1. Negative: Validation fails due to invalid name pattern (contains numbers).
     // Constraint: @Pattern(regexp = "^[a-zA-Z\\s.]+$")
     
    @Test
    void testAddAuthor_Negative_InvalidNamePattern() throws Exception {
        mockMvc.perform(post("/authors/add")
                .with(csrf())
                .param("authorName", "John123") // Numbers are forbidden by regex
                .param("authorEmail", "john@example.com")
                .param("authorCountry", "USA"))
                .andExpect(status().isOk()) // Returns to the form view
                .andExpect(view().name("author/author-add"))
                .andExpect(model().attributeHasFieldErrors("author", "authorName"));
    }

    
    // 2. Negative: Validation fails due to invalid email format.
     
    @Test
    void testAddAuthor_Negative_InvalidEmail() throws Exception {
        mockMvc.perform(post("/authors/add")
                .with(csrf())
                .param("authorName", "John Doe")
                .param("authorEmail", "not-an-email") // Fails @Email validation
                .param("authorCountry", "USA"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("author", "authorEmail"));
    }

    
     // 3. Negative: Edit non-existent author redirects to list.
     
    @Test
    void testEditAuthor_Negative_NotFound() throws Exception {
        // Arrange: Controller logic redirects to list if DTO is null
        when(authorService.getAuthorById(99)).thenReturn(null);
        when(authorService.toDTO(null)).thenReturn(null);

        mockMvc.perform(get("/authors/edit/99"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/authors/list"));
    }
}