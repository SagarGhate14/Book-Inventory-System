package com.cg.publishertest;

import com.cg.controller.PublisherController;
import com.cg.dto.PublisherDTO;
import com.cg.entity.Publisher;
import com.cg.service.PublisherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 3 controller tests for PublisherController
 */
@WebMvcTest(controllers = PublisherController.class)
// If you have Spring Security on classpath, uncomment the next line:
// @AutoConfigureMockMvc(addFilters = false)
class PublisherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublisherService publisherService;

    // 1) GET /publishers/list -> returns "publisher/publisher-list" with model attribute "publisherDTO"
    @Test
    void list_shouldRenderPublisherListWithDTOs() throws Exception {
        Publisher p1 = new Publisher();
        p1.setPublisherId(1);
        p1.setPublisherName("O'Reilly");
        p1.setAddress("CA");

        Publisher p2 = new Publisher();
        p2.setPublisherId(2);
        p2.setPublisherName("Pearson");
        p2.setAddress("UK");

        List<Publisher> entities = List.of(p1, p2);

        PublisherDTO dto1 = new PublisherDTO();
        dto1.setPublisherId(1);
        dto1.setPublisherName("O'Reilly");
        dto1.setAddress("CA");

        PublisherDTO dto2 = new PublisherDTO();
        dto2.setPublisherId(2);
        dto2.setPublisherName("Pearson");
        dto2.setAddress("UK");

        when(publisherService.getAllPublishers()).thenReturn(entities);
        when(publisherService.toDTOList(entities)).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/publishers/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("publisher/publisher-list"))
                .andExpect(model().attributeExists("publisherDTO"));
    }

    // 2) GET /publishers/new -> returns "publisher/publisher-add" with "publisherDTO" in model
    @Test
    void newForm_shouldRenderAddView() throws Exception {
        mockMvc.perform(get("/publishers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("publisher/publisher-add"))
                .andExpect(model().attributeExists("publisherDTO"));
    }

    // 3) POST /publishers/add -> maps DTO to entity, saves, and redirects to /publishers/list
    @Test
    void add_shouldMapDTOToEntity_andSave_thenRedirect() throws Exception {
        Publisher mapped = new Publisher();
        mapped.setPublisherId(0);
        mapped.setPublisherName("Manning");
        mapped.setAddress("USA");

        when(publisherService.toEntity(any(PublisherDTO.class))).thenReturn(mapped);
        when(publisherService.savePublisher(mapped)).thenReturn(mapped);

        mockMvc.perform(post("/publishers/add")
                        .param("publisherId", "0")
                        .param("publisherName", "Manning")
                        .param("address", "USA"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/publishers/list"));

        verify(publisherService, times(1)).toEntity(any(PublisherDTO.class));
        verify(publisherService, times(1)).savePublisher(any(Publisher.class));
    }
}
