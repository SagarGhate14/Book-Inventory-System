package com.cg.publishertest;

import com.cg.dto.PublisherDTO;
import com.cg.entity.Publisher;
import com.cg.exception.GlobalException.PublisherNotFoundException;
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
import java.util.Optional;

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
    void testFindById_Success() {
        // Arrange
        Publisher expectedPublisher = new Publisher();
        expectedPublisher.setPublisherId(101);
        expectedPublisher.setPublisherName("Oxford Press");

        when(publisherRepository.findById(101)).thenReturn(Optional.of(expectedPublisher));

        // Act
        Publisher actualPublisher = publisherService.findById(101);

        // Assert
        assertNotNull(actualPublisher);
        assertEquals("Oxford Press", actualPublisher.getPublisherName());
        verify(publisherRepository, times(1)).findById(101);
    }
    @Test
    void testDeletePublisher_Success() {
        // Arrange
        int pId = 202;
        when(publisherRepository.existsById(pId)).thenReturn(true);

        // Act
        publisherService.deletePublisher(pId);

        // Assert
        verify(publisherRepository, times(1)).existsById(pId);
        verify(publisherRepository, times(1)).deleteById(pId);
    }


    @Test
    void testDeletePublisher_ThrowsException_WhenIdNotFound() {
        // Arrange
        int pId = 999;
        when(publisherRepository.existsById(pId)).thenReturn(false);

        // Act & Assert
        assertThrows(PublisherNotFoundException.class, () -> {
            publisherService.deletePublisher(pId);
        });

        // Verify deleteById was NEVER called because validation failed
        verify(publisherRepository, never()).deleteById(pId);
    }

}
