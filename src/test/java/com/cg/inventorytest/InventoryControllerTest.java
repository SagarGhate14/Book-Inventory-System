package com.cg.inventorytest;

import com.cg.controller.InventoryController;

import com.cg.dto.InventoryDTO;
import com.cg.entity.Book;
import com.cg.entity.Inventory;
import com.cg.service.BookService;
import com.cg.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
@AutoConfigureMockMvc(addFilters = false) // Bypasses Spring Security for testing
class InventoryControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @MockBean
    private BookService bookService;

    // -------------------------------------------------------------------------
    // POSITIVE TEST CASES
    // -------------------------------------------------------------------------

    /**
     * 1. Positive: Successfully view the inventory list.
     */
    @Test
    void testGetAllInventories_Positive() throws Exception {
        when(inventoryService.getAllInventories()).thenReturn(new ArrayList<>());
        
        mockMvc.perform(get("/inventories/list")
                .with(csrf())) // CSRF token for Thymeleaf templates
                .andExpect(status().isOk())
                .andExpect(view().name("inventory/inventory-list"))
                .andExpect(model().attributeExists("inventories"));
    }

    /**
     * 2. Positive: Successfully save inventory when all inputs are valid.
     */
    @Test
    void testSaveInventory_Positive() throws Exception {
        // 1. Arrange: Create a real Inventory object to be returned by the mock
        Inventory mockInventory = new Inventory();
        
        // Tell the mock service: "When someone calls toEntity, return this object"
        when(inventoryService.toEntity(any(InventoryDTO.class))).thenReturn(mockInventory);
        
        // Also mock the book service call
        when(bookService.findIdByBook(anyInt())).thenReturn(new Book());

        // 2. Act & Assert
        mockMvc.perform(post("/inventories/save")
                .with(csrf())
                .param("status", "Available")
                .param("quantity", "10")
                .param("bookId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/inventories/list"));

        // 3. Verify
        verify(inventoryService, times(1)).saveInventory(any(Inventory.class));
    }



    /**
     * 3. Positive: Successfully delete an inventory record.
     */
    @Test
    void testDeleteInventory_Positive() throws Exception {
        mockMvc.perform(get("/inventories/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/inventories/list"));

        verify(inventoryService).deleteInventory(1);
    }

    // -------------------------------------------------------------------------
    // NEGATIVE TEST CASES
    // -------------------------------------------------------------------------

    /**
     * 1. Negative: Save fails because status does not match allowed Pattern.
     * Allowed: Available, Out of Stock, Damaged, Reserved.
    @ */
    @Test
    void testSave_Negative_InvalidStatus() throws Exception {
        mockMvc.perform(post("/inventories/save")
                .with(csrf())
                .param("status", "Sold")         // Violates @Pattern
                .param("quantity", "5")
                .param("bookId", "1"))
                .andExpect(status().isOk())      // Successfully stayed on the form page
                .andExpect(view().name("inventory/inventory-add"))
                .andExpect(model().attributeHasFieldErrors("inventory", "status"));
    }



    /**
     * 2. Negative: Update fails because of a duplicate book assignment.
     * Your controller logic checks if a book is already assigned to a different slot.
     */
    @Test
    void testUpdate_Negative_DuplicateBook() throws Exception {
        // Arrange: Mock existing inventory with a DIFFERENT ID to trigger duplicate check
        Inventory existing = new Inventory();
        existing.setInventoryId(50); // Different from the ID we are updating (1)
        
        when(inventoryService.findBookById(anyInt())).thenReturn(existing);

        // Act & Assert
        mockMvc.perform(post("/inventories/update")
                .with(csrf())
                .param("inventoryId", "1")
                .param("status", "Available")
                .param("bookId", "10")) // This bookId is already used by inventoryId 50
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("error"))
                .andExpect(redirectedUrl("/inventories/list"));
        
        // Verify update was never called
        verify(inventoryService, never()).updateInventory(anyInt(), any());
    }
}
