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

import java.util.ArrayList;
import java.util.List;

// FIXED: Added ArgumentMatchers and specific MockMvc imports
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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

	    // -------------------------------------------------------------------------
	    // POSITIVE TEST CASES
	    // -------------------------------------------------------------------------

	    /**
	     * 1. Positive: Successfully list all publishers.
	     */
	    @Test
	    void testGetAllPublishers_Positive() throws Exception {
	        when(publisherService.getAllPublishers()).thenReturn(new ArrayList<>());
	        
	        mockMvc.perform(get("/publishers/list")
	                .with(csrf())) // Provide CSRF for Thymeleaf
	                .andExpect(status().isOk())
	                .andExpect(view().name("publisher/publisher-list"))
	                .andExpect(model().attributeExists("publisherDTO"));
	    }

	    /**
	     * 2. Positive: Successfully save a publisher with valid data.
	     */
	    @Test
	    void testSavePublisher_Positive() throws Exception {
	        mockMvc.perform(post("/publishers/add")
	                .with(csrf())
	                .param("publisherName", "O'Reilly Media") // Valid (>2 chars)
	                .param("address", "1005 Gravenstein Hwy"))  // Valid (>4 chars)
	                .andExpect(status().is3xxRedirection())
	                .andExpect(redirectedUrl("/publishers/list"));

	        verify(publisherService, times(1)).savePublisher(any());
	    }

	    /**
	     * 3. Positive: Successfully delete a publisher.
	     */
	    @Test
	    void testDeletePublisher_Positive() throws Exception {
	        mockMvc.perform(get("/publishers/delete/1"))
	                .andExpect(status().is3xxRedirection())
	                .andExpect(redirectedUrl("/publishers/list"));

	        verify(publisherService).deletePublisher(1);
	    }

	    // -------------------------------------------------------------------------
	    // NEGATIVE TEST CASES
	    // -------------------------------------------------------------------------

	    /**
	     * 1. Negative: Fail to add publisher because Name is too short (triggers @Size).
	     */
	    @Test
	    void testSavePublisher_Negative_ShortName() throws Exception {
	        mockMvc.perform(post("/publishers/add")
	                .with(csrf())
	                .param("publisherName", "A") // Min size is 2
	                .param("address", "Valid Address"))
	                .andExpect(status().isOk()) // Returns to the form
	                .andExpect(view().name("publisher/publisher-add"))
	                .andExpect(model().attributeHasFieldErrors("publisherDTO", "publisherName"));
	    }

	    /**
	     * 2. Negative: Fail to add publisher because Address is missing (triggers @NotBlank).
	     */
	    @Test
	    void testSavePublisher_Negative_BlankAddress() throws Exception {
	        mockMvc.perform(post("/publishers/add")
	                .with(csrf())
	                .param("publisherName", "Valid Publisher")
	                .param("address", "")) // Blank triggers @NotBlank
	                .andExpect(status().isOk())
	                .andExpect(model().attributeHasFieldErrors("publisherDTO", "address"));
	    }

	    /**
	     * 3. Negative: Update fails because name is too long (triggers @Size max 100).
	     */
	    @Test
	    void testUpdatePublisher_Negative_LongName() throws Exception {
	        String longName = "A".repeat(101); // Exceeds max 100
	        
	        mockMvc.perform(post("/publishers/update")
	                .with(csrf())
	                .param("publisherId", "1")
	                .param("publisherName", longName)
	                .param("address", "Valid Address"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("publisher/publisher-edit")) // Stays on edit page
	                .andExpect(model().attributeHasFieldErrors("publisherDTO", "publisherName"));

	        verify(publisherService, never()).updatePublisher(any());
	    }
}
