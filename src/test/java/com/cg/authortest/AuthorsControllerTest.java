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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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

    // 1) GET /authors/list -> loads author list page with DTOs in model
    @Test
    void listAuthors_shouldRenderAuthorListWithDTOs() throws Exception {
        Author a1 = new Author();
        a1.setAuthorId(1);
        a1.setAuthorName("John Doe");
        a1.setAuthorEmail("john@gmail.com");
        a1.setAuthorCountry("USA");

        AuthorDTO dto1 = new AuthorDTO(1, "John Doe", "john@gmail.com", "USA");

        when(authorService.getAllAuthors()).thenReturn(List.of(a1));
        when(authorService.toDTOList(List.of(a1))).thenReturn(List.of(dto1));

        mockMvc.perform(get("/authors/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("author/author-list"))
                .andExpect(model().attributeExists("authors"));

        verify(authorService, times(1)).getAllAuthors();
        verify(authorService, times(1)).toDTOList(List.of(a1));
        verifyNoMoreInteractions(authorService);
    }

    // 2) GET /authors/new -> returns add page with empty Author model
    @Test
    void newAuthorForm_shouldRenderAddView() throws Exception {
        mockMvc.perform(get("/authors/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("author/author-add"))
                .andExpect(model().attributeExists("author"));
    }

    // 3) POST /authors/add -> saves Author and redirects to /authors/list
    @Test
    void addAuthor_shouldSaveAndRedirect() throws Exception {
        doNothing().when(authorService).saveAuthor(any(Author.class));

        mockMvc.perform(post("/authors/add")
                        .param("authorName", "Alice")
                        .param("authorEmail", "alice@gmail.com")
                        .param("authorCountry", "India"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/authors/list"));

        verify(authorService, times(1)).saveAuthor(any(Author.class));
        verifyNoMoreInteractions(authorService);
    }
}