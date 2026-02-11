package com.cg.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.cg.dto.AuthorDTO;
import com.cg.entity.Author;
import com.cg.exception.AuthorNotFoundException;
import com.cg.repository.AuthorRepository;


import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

      //Save author in db
    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    //Get all the authors
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    //finding author by ID
    public Author getAuthorById(int id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }

    //Delete author by Id
    public void deleteAuthor(int id) {
        if (!authorRepository.existsById(id)) {
            throw new AuthorNotFoundException(id);
        }
        authorRepository.deleteById(id);
    }

    //Update the author
    public Author updateAuthor(Author author) {
        int id = author.getAuthorId();
        if (!authorRepository.existsById(id)) {
            throw new AuthorNotFoundException(id);
        }
        return authorRepository.save(author);
    }

    
     // converting entity to DTO
    public AuthorDTO toDTO(Author author) {
        if (author == null) return null;
        return new AuthorDTO(
                author.getAuthorId(),
                author.getAuthorName(),
                author.getAuthorEmail(),
                author.getAuthorCountry()
        );
    }

    //converting DTO to entity
    public Author toEntity(AuthorDTO dto) {
        if (dto == null) return null;
        Author author = new Author();
        author.setAuthorId(dto.getAuthorId());
        author.setAuthorName(dto.getAuthorName());
        author.setAuthorEmail(dto.getAuthorEmail());
        author.setAuthorCountry(dto.getAuthorCountry());
        return author;
    }

    //Converting entityList to DTOList
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