package com.cg.inventorytest;

import com.cg.controller.InventoryController;
import com.cg.dto.InventoryDTO;
import com.cg.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc; // This tool mimics a browser (simulates requests)

    @MockBean
    private InventoryService inventoryService; // Create a fake service

   

    @Test
    void testSaveInventory() throws Exception {
        // Run the test: simulate filling a form and clicking Save
        mockMvc.perform(post("/inventories/save")
                        .param("quantity", "5"))
                .andExpect(status().is3xxRedirection()) // Check if it redirects
                .andExpect(redirectedUrl("/inventories/list")); // Check where it goes
    }

    @Test
    void testUpdateInventory() throws Exception {
        // Run the test: simulate updating an existing item
        mockMvc.perform(post("/inventories/update")
                        .param("inventoryId", "1")
                        .param("quantity", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/inventories/list"));
    }
    
    @Test
    void testDeleteInventory() throws Exception {
        // Run the test: simulate clicking the Delete link
        mockMvc.perform(get("/inventories/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/inventories/list"));
    }
}
