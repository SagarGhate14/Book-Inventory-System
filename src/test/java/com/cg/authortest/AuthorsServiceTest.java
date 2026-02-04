package com.cg.authortest;

import com.cg.dto.AuthorDTO;
import com.cg.entity.Author;
import com.cg.repository.AuthorRepository;
import com.cg.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorsServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author a1;
    private Author a2;

    @BeforeEach
    void setup() {
        a1 = new Author();
        a1.setAuthorId(1);
        a1.setAuthorName("John Doe");
        a1.setAuthorEmail("john@gmail.com");
        a1.setAuthorCountry("USA");

        a2 = new Author();
        a2.setAuthorId(2);
        a2.setAuthorName("Mary Jane");
        a2.setAuthorEmail("mary@gmail.com");
        a2.setAuthorCountry("UK");
    }

    @Test
    void testGetAllAuthors() {
        // 1. Create a list of ENTITIES (what the Repo returns)
        List<Author> entities = new ArrayList<>();
        entities.add(a1);
        entities.add(a2);

        // 2. Mock the Repo to return the ENTITY list
        when(authorRepository.findAll()).thenReturn(entities);

        // 3. Call the service (which converts them to DTOs)
        List<Author> result = authorService.getAllAuthors();

        // 4. Assertions
        assertNotNull(result, "The result list should not be null");
        assertEquals(2, result.size(), "The list size should be 2");
        
        // Check the first item in the list
        assertEquals("John Doe", result.get(0).getAuthorName());
        assertEquals(1, result.get(0).getAuthorId());
    }

    @Test
    void testMappingToDTO() {
        // Test single mapping
        AuthorDTO dto = authorService.toDTO(a1);
        
        assertNotNull(dto);
        assertEquals("John Doe", dto.getAuthorName());
        assertEquals(1, dto.getAuthorId());
    }

    @Test
    void testDeleteAuthor() {
        // ⛳️ FIX: Mock the exists check so the service 'if' block passes
        when(authorRepository.existsById(1)).thenReturn(true);

        authorService.deleteAuthor(1);

        // Verify repository was called
        verify(authorRepository, times(1)).deleteById(1);
    }
}
