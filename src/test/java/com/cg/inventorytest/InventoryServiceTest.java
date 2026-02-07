package com.cg.inventorytest;

import com.cg.dto.InventoryDTO;
import com.cg.entity.Book;
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
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    private Inventory sampleInventory;
    private Book sampleBook;

    @BeforeEach
    void setUp() {
        sampleBook = new Book();
        sampleBook.setBookId(101);
        sampleBook.setTitle("Java Basics");

        // Match your Inventory entity constructor or use setters
        sampleInventory = new Inventory();
        sampleInventory.setInventoryId(1);
        sampleInventory.setStatus("AVAILABLE");
        sampleInventory.setQuantity(10);
        sampleInventory.setBook(sampleBook);
    }

    @Test
    void testGetAllInventories() {
        // Arrange
        List<Inventory> list = new ArrayList<>();
        list.add(sampleInventory);
        when(inventoryRepository.findAll()).thenReturn(list);

        // Act
        List<Inventory> result = inventoryService.getAllInventories();

        // Assert
        assertEquals(1, result.size());
        assertEquals("AVAILABLE", result.get(0).getStatus());
        verify(inventoryRepository).findAll();
    }

  

    @Test
    void testSaveInventory() {
        // Arrange
        Inventory inventoryToSave = new Inventory();
        inventoryToSave.setQuantity(5);

        // Act
        inventoryService.saveInventory(inventoryToSave);

        // Assert
        verify(inventoryRepository).save(any(Inventory.class));
    }

 
    @Test
    void testDeleteInventory() {
        // Act
        inventoryService.deleteInventory(1);

        // Assert
        verify(inventoryRepository).deleteByInventoryId(1);
    }
}
