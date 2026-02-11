package com.cg.servicetest;

import com.cg.dto.InventoryDTO;
import com.cg.entity.Book;
import com.cg.entity.Inventory;
import com.cg.exception.GlobalException;
import com.cg.exception.InventoryNotFoundException;
import com.cg.repository.InventoryRepository;
import com.cg.service.BookService;
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

    @Mock
    private BookService bookService;

    @InjectMocks
    private InventoryService inventoryService;

    private Inventory sampleInventory;
    private InventoryDTO sampleDTO;
    private Book sampleBook;

    @BeforeEach
    void setUp() {
        sampleBook = new Book();
        sampleBook.setBookId(1);
        sampleBook.setTitle("Java Programming");

        sampleInventory = new Inventory();
        sampleInventory.setInventoryId(10);
        sampleInventory.setQuantity(5);
        sampleInventory.setStatus("AVAILABLE");
        sampleInventory.setBook(sampleBook);

        sampleDTO = new InventoryDTO();
        sampleDTO.setInventoryId(10);
        sampleDTO.setQuantity(20);
        sampleDTO.setBookId(1);
        sampleDTO.setStatus("Available");
    }

    // -------------------------------------------------------------------------
    // POSITIVE TEST CASES
    // -------------------------------------------------------------------------

    /**
     * 1. Positive: Successfully save an inventory record.
     */
    @Test
    void testSaveInventory_Positive() {
        // Act
        inventoryService.saveInventory(sampleInventory);

        // Assert: Verify save was called on the repository
        verify(inventoryRepository, times(1)).save(sampleInventory);
    }

    /**
     * 2. Positive: Successfully update an existing inventory record.
     * Verifies that quantity, status, and book linkage are updated.
     */
    @Test
    void testUpdateInventory_Positive() {
        // Arrange
        when(inventoryRepository.findById(10)).thenReturn(Optional.of(sampleInventory));
        when(bookService.findIdByBook(1)).thenReturn(sampleBook);

        // Act
        inventoryService.updateInventory(10, sampleDTO);

        // Assert
        assertEquals(20, sampleInventory.getQuantity());
        assertEquals("AVAILABLE", sampleInventory.getStatus()); // quantity > 0
        verify(inventoryRepository, times(1)).save(sampleInventory);
    }

    /**
     * 3. Positive: Verify the Entity to DTO conversion (toDTO).
     */
    @Test
    void testToDTO_Positive() {
        // Act
        InventoryDTO result = inventoryService.toDTO(sampleInventory);

        // Assert
        assertNotNull(result);
        assertEquals(10, result.getInventoryId());
        assertEquals("Java Programming", result.getBookTitle());
    }

    // -------------------------------------------------------------------------
    // NEGATIVE TEST CASES
    // -------------------------------------------------------------------------

    /**
     * 1. Negative: Handle update for a non-existent inventory ID.
     */
    @Test
    void testUpdateInventory_Negative_NotFound() {
        // Arrange: Repository returns empty for ID 99
        when(inventoryRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert: Should throw RuntimeException as per service logic
        assertThrows(RuntimeException.class, () -> {
            inventoryService.updateInventory(99, sampleDTO);
        });

        // Verify save was never called
        verify(inventoryRepository, never()).save(any());
    }

    /**
     * 2. Negative: Handle searching for an inventory record that doesn't exist.
     */
    @Test
    void testGetInventoryById_Negative_NotFound() {
        // Arrange
        when(inventoryRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        // Change from java.util.NoSuchElementException to your custom exception
        assertThrows(InventoryNotFoundException.class, () -> {
            inventoryService.getInventoryById(999);
        });
    }


    /**
     * 3. Negative: Verify toEntityList returns empty when given null.
     */
    @Test
    void testToDTOList_Negative_NullInput() {
        // Act
        List<InventoryDTO> result = inventoryService.toDTOList(new ArrayList<>());

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
