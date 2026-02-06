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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// If Spring Security is on your classpath, this disables filters to prevent 403s.
@WebMvcTest(controllers = PublisherController.class)
@AutoConfigureMockMvc(addFilters = false)
class PublisherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublisherService publisherService;

    @Test
    void getAllPublishers_ShouldReturnListViewWithModel() throws Exception {
        List<Publisher> mockList = List.of(new Publisher());
        List<PublisherDTO> mockDtoList = List.of(new PublisherDTO());

        when(publisherService.getAllPublishers()).thenReturn(mockList);
        when(publisherService.toDTOList(mockList)).thenReturn(mockDtoList);

        mockMvc.perform(get("/publishers/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("publisher/publisher-list"))
                .andExpect(model().attribute("publisherDTO", mockDtoList));

        verify(publisherService, times(1)).getAllPublishers();
        verify(publisherService, times(1)).toDTOList(mockList);
        verifyNoMoreInteractions(publisherService);
    }

    @Test
    void savePublisher_ShouldMapDTO_Save_AndRedirectToList() throws Exception {
        Publisher mappedEntity = new Publisher();
        when(publisherService.toEntity(any(PublisherDTO.class))).thenReturn(mappedEntity);

        mockMvc.perform(post("/publishers/add")
                        .param("publisherName", "Test Publisher")
                        .param("address", "Test Address"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/publishers/list"));

        verify(publisherService, times(1)).toEntity(any(PublisherDTO.class));
        verify(publisherService, times(1)).savePublisher(any(Publisher.class));
        verifyNoMoreInteractions(publisherService);
    }

    @Test
    void deletePublisher_ShouldCallServiceAndRedirectToList() throws Exception {
        int id = 1;

        mockMvc.perform(get("/publishers/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/publishers/list"));

        verify(publisherService, times(1)).deletePublisher(id);
        verifyNoMoreInteractions(publisherService);
    }
}