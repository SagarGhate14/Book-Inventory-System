package com.cg.authortest;


import com.cg.dto.AuthorDTO;
import com.cg.entity.Author;
import com.cg.repository.AuthorRepository;
import com.cg.service.AuthorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * 3 Tests for AuthorService
 */
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

    // 1️⃣ getAllAuthors → returns list from repo
    @Test
    void getAllAuthors_shouldReturnRepoList() {
        when(authorRepository.findAll()).thenReturn(List.of(a1, a2));

        List<Author> list = authorService.getAllAuthors();

        assertThat(list).hasSize(2);
        assertThat(list.get(0).getAuthorName()).isEqualTo("John Doe");
    }

    // 2️⃣ toDTOList → converts Entities to DTOs
    @Test
    void toDTOList_shouldConvertEntityListToDTOList() {
        List<AuthorDTO> dtos = authorService.toDTOList(List.of(a1));

        assertThat(dtos).hasSize(1);
        assertThat(dtos.get(0).getAuthorName()).isEqualTo("John Doe");
    }

    // 3️⃣ deleteAuthor → calls repository.deleteById()
    @Test
    void deleteAuthor_shouldCallRepoDeleteById() {
        doNothing().when(authorRepository).deleteById(1);

        authorService.deleteAuthor(1);

        verify(authorRepository, times(1)).deleteById(1);
    }
}
