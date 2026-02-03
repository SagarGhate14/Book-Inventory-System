package com.cg.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dto.PublisherDTO;
import com.cg.entity.Publisher;
import com.cg.repository.PublisherRepository;


@Service
public class PublisherService implements IPublisherService{

	@Autowired
	PublisherRepository publisherRepository;
	
	@Override
	public List<Publisher> getAllPublishers() {
		return publisherRepository.findAll();
	}


	@Override
	public Publisher savePublisher(Publisher publisher) {
		return publisherRepository.save(publisher);
	}



	@Override
	public Publisher updatePublisher(Publisher publisher) {
	       return publisherRepository.save(publisher);
	}



	@Override
	public void deletePublisher(int pId) {
           publisherRepository.deleteById(pId);		
	}
	
	public Publisher findById(int pId) {
		return publisherRepository.findById(pId).get();
	}
	
	
	
	
	
	 public  Publisher toEntity(PublisherDTO dto) {
	        if (dto == null) return null;

	        Publisher entity = new Publisher();
	        entity.setPublisherId(dto.getPublisherId());
	        entity.setPublisherName(dto.getPublisherName());
	        entity.setAddress(dto.getAddress());
	        // Note: Books list is usually handled separately in the service layer 
	        // to avoid unintended cascading updates.
	        return entity;
	    }
	 
	 public PublisherDTO toDTO(Publisher entity) {
	        if (entity == null) {
	            return null;
	        }

	        PublisherDTO dto = new PublisherDTO();
	        dto.setPublisherId(entity.getPublisherId());
	        dto.setPublisherName(entity.getPublisherName());
	        dto.setAddress(entity.getAddress());
	        
	        return dto;
	    }

	 
	 public List<Publisher> toEntityList(List<PublisherDTO> dtoList) {
	        if (dtoList == null) return null;

	        List<Publisher> entities = new ArrayList<>();
	        for (PublisherDTO dto : dtoList) {
	            entities.add(this.toEntity(dto));
	        }
	        return entities;
	    }
	 
	 public List<PublisherDTO> toDTOList(List<Publisher> entityList) {
	        if (entityList == null) return null;

	        List<PublisherDTO> dtos = new ArrayList<>();
	        for (Publisher entity : entityList) {
	            dtos.add(this.toDTO(entity));
	        }
	        return dtos;
	    }


	
}
