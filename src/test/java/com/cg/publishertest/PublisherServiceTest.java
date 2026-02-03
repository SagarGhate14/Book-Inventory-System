package com.cg.publishertest;


import com.cg.dto.PublisherDTO;
import com.cg.entity.Publisher;
import com.cg.repository.PublisherRepository;
import com.cg.service.PublisherService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * 3 service tests for PublisherService
 */
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

    // 1) getAllPublishers returns repository list
    @Test
    void getAllPublishers_shouldReturnListFromRepository() {
        when(publisherRepository.findAll()).thenReturn(List.of(p1, p2));

        List<Publisher> result = publisherService.getAllPublishers();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getPublisherId()).isEqualTo(1);
        assertThat(result.get(0).getPublisherName()).isEqualTo("O'Reilly");
        assertThat(result.get(1).getPublisherId()).isEqualTo(2);
        assertThat(result.get(1).getPublisherName()).isEqualTo("Pearson");
    }

    // 2) toDTOList maps entities to DTOs (covers toDTO as well)
    @Test
    void toDTOList_shouldMapEntitiesToDTOs() {
        List<PublisherDTO> dtos = publisherService.toDTOList(List.of(p1, p2));

        assertThat(dtos).hasSize(2);
        assertThat(dtos.get(0).getPublisherId()).isEqualTo(1);
        assertThat(dtos.get(0).getPublisherName()).isEqualTo("O'Reilly");
        assertThat(dtos.get(0).getAddress()).isEqualTo("CA");

        assertThat(dtos.get(1).getPublisherId()).isEqualTo(2);
        assertThat(dtos.get(1).getPublisherName()).isEqualTo("Pearson");
        assertThat(dtos.get(1).getAddress()).isEqualTo("UK");
    }

    // 3) deletePublisher delegates to repository.deleteById
    @Test
    void deletePublisher_shouldInvokeRepositoryDeleteById() {
        doNothing().when(publisherRepository).deleteById(2);

        publisherService.deletePublisher(2);

        verify(publisherRepository, times(1)).deleteById(2);
    }
}
