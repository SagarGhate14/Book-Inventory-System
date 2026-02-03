package com.cg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import com.cg.controller.AuthorController;
import com.cg.dto.AuthorDTO;
import com.cg.entity.Author;
import com.cg.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public void saveAuthor (Author author) {
        authorRepository.save(author);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(int id) {
        return authorRepository. findById(id).orElse(null);
    }

    public void deleteAuthor(int id) {
        authorRepository.deleteById(id);
    }
    
    
    public AuthorDTO toDTO(Author author) {
    	  if(author == null) {
    		  return null;
    	  }
    	  return new AuthorDTO(author.getAuthorId(),
    			  author.getAuthorName(),
    			  author.getAuthorEmail(),
    			  author.getAuthorCountry());
    }
    
    public List<AuthorDTO> toDTOList(List<Author> entities){
    	
    	List<AuthorDTO> authorsDTO = new ArrayList<>();
        
        if (entities == null) return authorsDTO;
        for (Author e : entities) {
        	
            AuthorDTO dto = toDTO(e);
            
            authorsDTO.add(dto);
        }
        
        return authorsDTO;
    }
}