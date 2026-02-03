package com.cg.test;

import com.cg.controller.InventoryController;
import com.cg.dto.InventoryDTO;
import com.cg.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    // 1) GET /inventories/list – renders dashboard with inventories
    @Test
    void list_shouldRenderInventoryViewWithModel() throws Exception {
        InventoryDTO dto1 = new InventoryDTO();
        dto1.setInventoryId(1);
        dto1.setQuantity(10);
        dto1.setStatus("Available");

        InventoryDTO dto2 = new InventoryDTO();
        dto2.setInventoryId(2);
        dto2.setQuantity(0);
        dto2.setStatus("Out of Stock");

        when(inventoryService.getAllInventories()).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/inventories/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("inventory/inventory"))
                .andExpect(model().attributeExists("inventories"))
                .andExpect(model().attribute("inventories", hasSize(2)));
    }

    // 2) POST /inventories/save – redirects to list and calls service
    @Test
    void save_shouldRedirectToList() throws Exception {
        doNothing().when(inventoryService).saveInventory(any(InventoryDTO.class));

        mockMvc.perform(post("/inventories/save")
                        .param("quantity", "7"))   // binds to InventoryDTO.quantity
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/inventories/list"));

        verify(inventoryService, times(1)).saveInventory(any(InventoryDTO.class));
    }

    // 3) POST /inventories/update – redirects to list and calls service with id + dto
    @Test
    void update_shouldRedirectToList() throws Exception {
        doNothing().when(inventoryService).updateInventory(anyInt(), any(InventoryDTO.class));

        mockMvc.perform(post("/inventories/update")
                        .param("inventoryId", "9")  // binds to InventoryDTO.inventoryId
                        .param("quantity", "0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/inventories/list"));

        verify(inventoryService, times(1)).updateInventory(eq(9), any(InventoryDTO.class));
    }
}
