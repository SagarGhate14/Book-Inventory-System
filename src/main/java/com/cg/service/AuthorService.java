package com.cg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dto.AuthorDTO;
import com.cg.entity.Author;
import com.cg.repository.AuthorRepository;

import static com.cg.exception.GlobalException.AuthorNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;


    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    
    public Author getAuthorById(int id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }

    public void deleteAuthor(int id) {
        if (!authorRepository.existsById(id)) {
            throw new AuthorNotFoundException(id);
        }
        authorRepository.deleteById(id);
    }

    public Author updateAuthor(Author author) {
        int id = author.getAuthorId();
        if (!authorRepository.existsById(id)) {
            throw new AuthorNotFoundException(id);
        }
        return authorRepository.save(author);
    }

    

    public AuthorDTO toDTO(Author author) {
        if (author == null) return null;
        return new AuthorDTO(
                author.getAuthorId(),
                author.getAuthorName(),
                author.getAuthorEmail(),
                author.getAuthorCountry()
        );
    }

    public Author toEntity(AuthorDTO dto) {
        if (dto == null) return null;
        Author author = new Author();
        author.setAuthorId(dto.getAuthorId());
        author.setAuthorName(dto.getAuthorName());
        author.setAuthorEmail(dto.getAuthorEmail());
        author.setAuthorCountry(dto.getAuthorCountry());
        return author;
    }

    public List<AuthorDTO> toDTOList(List<Author> entities) {
        List<AuthorDTO> authorsDTO = new ArrayList<>();
        if (entities == null) return authorsDTO;
        for (Author e : entities) {
            AuthorDTO dto = toDTO(e);
            authorsDTO.add(dto);
        }
        return authorsDTO;
    }
}