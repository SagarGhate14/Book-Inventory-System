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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    void testShowAddForm_ShouldPopulateModel() throws Exception {
        // Arrange
        when(bookService.getAllBooks()).thenReturn(List.of(new Book()));

        // Act & Assert
        mockMvc.perform(get("/inventories/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("inventory/inventory-add"))
                .andExpect(model().attributeExists("inventory", "books"));
    }

    @Test
    void testSaveInventory_ShouldRedirectAfterPost() throws Exception {
        // Arrange
        InventoryDTO dto = new InventoryDTO();
        dto.setBookId(1);
        dto.setQuantity(10);

        when(inventoryService.toEntity(any(InventoryDTO.class))).thenReturn(new Inventory());
        when(bookService.findIdByBook(1)).thenReturn(new Book());

        // Act & Assert
        mockMvc.perform(post("/inventories/save")
                        .flashAttr("inventory", dto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/inventories/list"));

        verify(inventoryService, times(1)).saveInventory(any(Inventory.class));
    }
}
