package com.cg.service;
import com.cg.dto.InventoryDTO;
import java.util.List;

public interface InventoryService {
    List<InventoryDTO> getAllInventories();
    InventoryDTO getInventoryById(Long id);
    void saveInventory(InventoryDTO inventoryDTO);
    void updateInventory(Long id, InventoryDTO inventoryDTO);
    void deleteInventory(Long id);
}
