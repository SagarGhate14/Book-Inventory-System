package com.cg.service;

import com.cg.dto.InventoryDTO;
import java.util.List;

public interface InventoryService {
    List<InventoryDTO> getAllInventories();
    InventoryDTO getInventoryById(int id);
    void saveInventory(InventoryDTO dto);
    void updateInventory(int id, InventoryDTO dto);
    void deleteInventory(int id);
}
