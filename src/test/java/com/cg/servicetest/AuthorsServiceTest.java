package com.cg.servicetest;

import com.cg.dto.AuthorDTO;
import com.cg.entity.Author;
import com.cg.exception.GlobalException.AuthorNotFoundException;
import com.cg.repository.AuthorRepository;
import com.cg.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorsServiceTest {

	 @Mock
	    private AuthorRepository authorRepository;

	    @InjectMocks
	    private AuthorService authorService;

	    private Author sampleAuthor;

	    @BeforeEach
	    void setUp() {
	        // Initialize a sample Author object before each test
	        sampleAuthor = new Author();
	        sampleAuthor.setAuthorId(101);
	        sampleAuthor.setAuthorName("J.K. Rowling");
	        sampleAuthor.setAuthorEmail("jk@example.com");
	        sampleAuthor.setAuthorCountry("UK");
	    }

	    // --- POSITIVE TEST CASES ---

	    /**
	     * 1. Positive: Successfully retrieve an author when a valid ID exists.
	     */
	    @Test
	    void testGetAuthorById_Positive() {
	        // Arrange: Tell the mock repository to return the sample author for ID 101
	        when(authorRepository.findById(101)).thenReturn(Optional.of(sampleAuthor));

	        // Act: Call the service method
	        Author result = authorService.getAuthorById(101);

	        // Assert: Verify the result is not null and the data matches
	        assertNotNull(result);
	        assertEquals("J.K. Rowling", result.getAuthorName());
	        verify(authorRepository, times(1)).findById(101);
	    }

	    /**
	     * 2. Positive: Successfully retrieve all authors from the database.
	     */
	    @Test
	    void testGetAllAuthors_Positive() {
	        // Arrange: Mock the list returned by the repository
	        List<Author> authors = Arrays.asList(sampleAuthor, new Author());
	        when(authorRepository.findAll()).thenReturn(authors);

	        // Act: Call the service
	        List<Author> resultList = authorService.getAllAuthors();

	        // Assert: Verify list size and repository interaction
	        assertEquals(2, resultList.size());
	        verify(authorRepository, times(1)).findAll();
	    }

	    /**
	     * 3. Positive: Successfully update an existing author.
	     */
	    @Test
	    void testUpdateAuthor_Positive() {
	        // Arrange: Mock existence check and save operation
	        when(authorRepository.existsById(101)).thenReturn(true);
	        when(authorRepository.save(any(Author.class))).thenReturn(sampleAuthor);

	        // Act: Perform update
	        Author updatedAuthor = authorService.updateAuthor(sampleAuthor);

	        // Assert: Verify update was successful
	        assertNotNull(updatedAuthor);
	        assertEquals(101, updatedAuthor.getAuthorId());
	        verify(authorRepository, times(1)).save(sampleAuthor);
	    }

	    // --- NEGATIVE TEST CASES ---

	    /**
	     * 1. Negative: Throw AuthorNotFoundException when searching for a non-existent ID.
	     */
	    @Test
	    void testGetAuthorById_Negative_NotFound() {
	        // Arrange: Mock repository to return empty for ID 999
	        when(authorRepository.findById(999)).thenReturn(Optional.empty());

	        // Act & Assert: Verify that the specific exception is thrown
	        assertThrows(AuthorNotFoundException.class, () -> {
	            authorService.getAuthorById(999);
	        });
	    }

	    /**
	     * 2. Negative: Throw AuthorNotFoundException when attempting to delete a non-existent author.
	     */
	    @Test
	    void testDeleteAuthor_Negative_NotFound() {
	        // Arrange: Mock existsById to return false
	        when(authorRepository.existsById(999)).thenReturn(false);

	        // Act & Assert: Should throw exception before reaching the delete call
	        assertThrows(AuthorNotFoundException.class, () -> {
	            authorService.deleteAuthor(999);
	        });
	        
	        // Verify deleteById was NEVER called because the check failed
	        verify(authorRepository, never()).deleteById(999);
	    }

	    /**
	     * 3. Negative: Throw AuthorNotFoundException when updating an author who does not exist.
	     */
	    @Test
	    void testUpdateAuthor_Negative_NotFound() {
	        // Arrange: Mock existsById to return false for the author's ID
	        when(authorRepository.existsById(101)).thenReturn(false);

	        // Act & Assert: Verify exception
	        assertThrows(AuthorNotFoundException.class, () -> {
	            authorService.updateAuthor(sampleAuthor);
	        });

	        // Verify save was NEVER called
	        verify(authorRepository, never()).save(any(Author.class));
	    }
}
