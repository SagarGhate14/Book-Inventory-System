package com.cg.publishertest;

import com.cg.dto.PublisherDTO;
import com.cg.entity.Publisher;
import com.cg.exception.GlobalException.BadRequestException;
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

    private Publisher samplePublisher;

    @BeforeEach
    void setUp() {
        // Initialize a sample publisher for testing
        samplePublisher = new Publisher();
        samplePublisher.setPublisherId(10);
        samplePublisher.setPublisherName("Pearson Education");
        samplePublisher.setAddress("London, UK");
    }

    // -------------------------------------------------------------------------
    // POSITIVE TEST CASES
    // -------------------------------------------------------------------------

    /**
     * 1. Positive: Successfully save a publisher.
     */
    @Test
    void testSavePublisher_Positive() {
        // Arrange
        when(publisherRepository.save(any(Publisher.class))).thenReturn(samplePublisher);

        // Act
        Publisher saved = publisherService.savePublisher(samplePublisher);

        // Assert
        assertNotNull(saved);
        assertEquals("Pearson Education", saved.getPublisherName());
        verify(publisherRepository, times(1)).save(samplePublisher);
    }

    /**
     * 2. Positive: Successfully find a publisher by valid ID.
     */
    @Test
    void testFindById_Positive() {
        // Arrange
        when(publisherRepository.findById(10)).thenReturn(Optional.of(samplePublisher));

        // Act
        Publisher found = publisherService.findById(10);

        // Assert
        assertNotNull(found);
        assertEquals(10, found.getPublisherId());
        verify(publisherRepository, times(1)).findById(10);
    }

    /**
     * 3. Positive: Successfully update an existing publisher.
     */
    @Test
    void testUpdatePublisher_Positive() {
        // Arrange: Mock existence check and save operation
        when(publisherRepository.existsById(10)).thenReturn(true);
        when(publisherRepository.save(any(Publisher.class))).thenReturn(samplePublisher);

        // Act
        Publisher result = publisherService.updatePublisher(samplePublisher);

        // Assert
        assertNotNull(result);
        verify(publisherRepository, times(1)).save(samplePublisher);
    }

    // -------------------------------------------------------------------------
    // NEGATIVE TEST CASES
    // -------------------------------------------------------------------------

    /**
     * 1. Negative: Throw BadRequestException when saving a null publisher.
     */
    @Test
    void testSavePublisher_Negative_NullInput() {
        // Act & Assert
        assertThrows(BadRequestException.class, () -> {
            publisherService.savePublisher(null);
        });
        verify(publisherRepository, never()).save(any());
    }

    /**
     * 2. Negative: Throw PublisherNotFoundException when deleting a non-existent ID.
     */
    @Test
    void testDeletePublisher_Negative_NotFound() {
        // Arrange
        when(publisherRepository.existsById(99)).thenReturn(false);

        // Act & Assert
        assertThrows(PublisherNotFoundException.class, () -> {
            publisherService.deletePublisher(99);
        });
        verify(publisherRepository, never()).deleteById(99);
    }

    /**
     * 3. Negative: Throw PublisherNotFoundException when finding a non-existent ID.
     */
    @Test
    void testFindById_Negative_NotFound() {
        // Arrange
        when(publisherRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PublisherNotFoundException.class, () -> {
            publisherService.findById(99);
        });
    }

}
