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

// FIXED: Added ArgumentMatchers and specific MockMvc imports
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PublisherController.class)
@AutoConfigureMockMvc(addFilters = false)
class PublisherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublisherService publisherService;



    @Test
    void testSavePublisher() throws Exception {
        when(publisherService.toEntity(any(PublisherDTO.class))).thenReturn(new Publisher());
        
        mockMvc.perform(post("/publishers/add")
                        .flashAttr("publisherDTO", new PublisherDTO()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/publishers/list"));

        verify(publisherService).savePublisher(any(Publisher.class));
    }

    @Test
    void testDeletePublisher() throws Exception {
        mockMvc.perform(get("/publishers/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/publishers/list"));

        verify(publisherService).deletePublisher(1);
    }
}
