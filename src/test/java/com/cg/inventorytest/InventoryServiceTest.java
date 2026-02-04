package com.cg.inventorytest;

import com.cg.dto.InventoryDTO;
import com.cg.entity.Inventory;
import com.cg.repository.InventoryRepository;
import com.cg.service.InventoryService;
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
public class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository; // Fake Database

    @InjectMocks
    private InventoryService inventoryService; // Service we are testing

    private Inventory sampleInventory;

    @BeforeEach
    void setUp() {
        // Create a sample entity for testing
        // Arguments: id, status, quantity, book, user
        sampleInventory = new Inventory(1, "Available", 10, null, null);
    }

    @Test
    void testGetAllInventories() {
        // 1. Prepare fake list
        List<Inventory> list = new ArrayList<>();
        list.add(sampleInventory);

        // 2. Mock the repository call
        when(inventoryRepository.findAll()).thenReturn(list);

        // 3. Call the service
        List<InventoryDTO> result = inventoryService.getAllInventories();

        // 4. Simple Check
        assertEquals(1, result.size());
        assertEquals("Available", result.get(0).getStatus());
    }

    @Test
    void testGetInventoryById() {
        // Mock finding the item
        when(inventoryRepository.findById(1)).thenReturn(Optional.of(sampleInventory));

        InventoryDTO result = inventoryService.getInventoryById(1);

        // Simple Check
        assertNotNull(result);
        assertEquals(1, result.getInventoryId());
    }

    @Test
    void testSaveInventory() {
        // Prepare DTO to save
        InventoryDTO dto = new InventoryDTO();
        dto.setQuantity(5);

        // Call the service
        inventoryService.saveInventory(dto);

        // Verify that save was called on the database
        verify(inventoryRepository).save(any(Inventory.class));
    }

   

  

}
