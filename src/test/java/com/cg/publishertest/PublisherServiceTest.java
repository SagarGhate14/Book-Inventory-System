package com.cg.publishertest;

import com.cg.dto.PublisherDTO;
import com.cg.entity.Publisher;
import com.cg.repository.PublisherRepository;
import com.cg.service.PublisherService;
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
public class PublisherServiceTest {

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherService publisherService;

    private Publisher p1;
    private Publisher p2;

    @BeforeEach
    void setUp() {
        p1 = new Publisher();
        p1.setPublisherId(1);
        p1.setPublisherName("O'Reilly");
        p1.setAddress("CA");

        p2 = new Publisher();
        p2.setPublisherId(2);
        p2.setPublisherName("Pearson");
        p2.setAddress("UK");
    }

    @Test
    void testGetAllPublishers() {
        List<Publisher> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);

        when(publisherRepository.findAll()).thenReturn(list);

        List<Publisher> result = publisherService.getAllPublishers();

        assertEquals(2, result.size());
        assertEquals("O'Reilly", result.get(0).getPublisherName());
    }

    @Test
    void testToDTOListMapping() {
        List<Publisher> entities = new ArrayList<>();
        entities.add(p1);

        List<PublisherDTO> result = publisherService.toDTOList(entities);

        assertNotNull(result);
        assertEquals(1, result.get(0).getPublisherId());
        assertEquals("O'Reilly", result.get(0).getPublisherName());
    }

    @Test
    void testDeletePublisher() {
        // ⛳️ FIX: You must tell the mock that ID 2 exists 
        // so the 'if(!existsById)' check in your service passes!
        when(publisherRepository.existsById(2)).thenReturn(true);

        publisherService.deletePublisher(2);

        // Verify the delete was actually called
        verify(publisherRepository, times(1)).deleteById(2);
    }
}
