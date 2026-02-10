package com.cg.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dto.PublisherDTO;
import com.cg.entity.Publisher;
import com.cg.exception.GlobalException;
import com.cg.repository.PublisherRepository;

// ✅ Using static inner exceptions from GlobalException
import static com.cg.exception.GlobalException.PublisherNotFoundException;
import static com.cg.exception.GlobalException.BadRequestException;

@Service
public class PublisherService implements IPublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    @Override
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    @Override
    public Publisher savePublisher(Publisher publisher) {
        if (publisher == null) {
            throw new BadRequestException("Publisher details are required.");
        }
        return publisherRepository.save(publisher);
    }

    @Override
    public Publisher updatePublisher(Publisher publisher) {
        if (publisher == null) {
            throw new BadRequestException("Publisher update body is required.");
        }
        // Verify existence before saving
        if (!publisherRepository.existsById(publisher.getPublisherId())) {
            throw new PublisherNotFoundException(publisher.getPublisherId());
        }
        return publisherRepository.save(publisher);
    }

    @Override
    public void deletePublisher(int pId) {
        // Verify existence before deleting
        if (!publisherRepository.existsById(pId)) {
            throw new PublisherNotFoundException(pId);
        }
        publisherRepository.deleteById(pId);
    }

    @Override
    public Publisher findById(int pId) {
        return publisherRepository.findById(pId)
                .orElseThrow(() -> new GlobalException.PublisherNotFoundException(pId));
    }


    /* --- MAPPING HELPERS (Logic Unchanged) --- */

    public Publisher toEntity(PublisherDTO dto) {
        if (dto == null) return null;
        Publisher entity = new Publisher();
        entity.setPublisherId(dto.getPublisherId());
        entity.setPublisherName(dto.getPublisherName());
        entity.setAddress(dto.getAddress());
        return entity;
    }

    public PublisherDTO toDTO(Publisher entity) {
        if (entity == null) return null;
        PublisherDTO dto = new PublisherDTO();
        dto.setPublisherId(entity.getPublisherId());
        dto.setPublisherName(entity.getPublisherName());
        dto.setAddress(entity.getAddress());
        return dto;
    }

    public List<Publisher> toEntityList(List<PublisherDTO> dtoList) {
        if (dtoList == null) return new ArrayList<>();
        List<Publisher> entities = new ArrayList<>();
        for (PublisherDTO dto : dtoList) {
            entities.add(this.toEntity(dto));
        }
        return entities;
    }

    public List<PublisherDTO> toDTOList(List<Publisher> entityList) {
        if (entityList == null) return new ArrayList<>();
        List<PublisherDTO> dtos = new ArrayList<>();
        for (Publisher entity : entityList) {
            dtos.add(this.toDTO(entity));
        }
        return dtos;
    }
}
