package com.cg.inventorytest;

import com.cg.dto.InventoryDTO;
import com.cg.entity.Inventory;
import com.cg.repository.InventoryRepository;
import com.cg.service.InventoryServiceImplementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventoryService {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryServiceImplementation inventoryService;

    private Inventory inv1;
    private Inventory inv2;

    @BeforeEach
    void setUp() {
        inv1 = new Inventory();
        setInventoryIdReflectively(inv1, 1);
        inv1.setQuantity(10);
        inv1.setStatus("Available");

        inv2 = new Inventory();
        setInventoryIdReflectively(inv2, 2);
        inv2.setQuantity(0);
        inv2.setStatus("Out of Stock");
    }

    /**
     * Helper to set id even if entity uses a different private field name (inventoryId or id).
     */
    private void setInventoryIdReflectively(Inventory inv, int id) {
        try {
            Field f = null;
            try { f = Inventory.class.getDeclaredField("inventoryId"); }
            catch (NoSuchFieldException e) { f = Inventory.class.getDeclaredField("id"); }
            f.setAccessible(true);
            // Support int or Long
            if (f.getType().equals(int.class) || f.getType().equals(Integer.class)) {
                f.set(inv, id);
            } else {
                f.set(inv, (long) id);
            }
        } catch (Exception ignore) {}
    }

    // 1) getAllInventories maps Entity -> DTO list
    @Test
    void getAllInventories_returnsMappedDTOs() {
        when(inventoryRepository.findAll()).thenReturn(Arrays.asList(inv1, inv2));

        List<InventoryDTO> dtos = inventoryService.getAllInventories();

        assertThat(dtos).hasSize(2);
        assertThat(dtos.get(0).getInventoryId()).isEqualTo(1);
        assertThat(dtos.get(0).getQuantity()).isEqualTo(10);
        assertThat(dtos.get(0).getStatus()).isEqualTo("Available");

        assertThat(dtos.get(1).getInventoryId()).isEqualTo(2);
        assertThat(dtos.get(1).getQuantity()).isEqualTo(0);
        assertThat(dtos.get(1).getStatus()).isEqualTo("Out of Stock");
    }

    // 2) getInventoryById returns DTO when found and throws when not found
    @Test
    void getInventoryById_found_returnsDTO() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.of(inv1));

        InventoryDTO dto = inventoryService.getInventoryById(1);

        assertThat(dto.getInventoryId()).isEqualTo(1);
        assertThat(dto.getQuantity()).isEqualTo(10);
        assertThat(dto.getStatus()).isEqualTo("Available");
    }

    // 3) updateInventory updates quantity & status according to logic
    @Test
    void updateInventory_updatesQuantityAndStatus() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.of(inv1));

        InventoryDTO update = new InventoryDTO();
        update.setQuantity(0); // should set status to "Out of Stock"

        inventoryService.updateInventory(1, update);

        verify(inventoryRepository).save(inv1);
        assertThat(inv1.getQuantity()).isEqualTo(0);
        assertThat(inv1.getStatus()).isEqualTo("Out of Stock");
    }
}