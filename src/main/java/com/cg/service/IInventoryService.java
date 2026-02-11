package com.cg.service;


import com.cg.dto.InventoryDTO;
import com.cg.entity.Inventory;

import java.util.List;

public interface IInventoryService {
    List<Inventory> getAllInventories();
    Inventory getInventoryById(int id);
    void saveInventory(Inventory dto);
    void deleteInventory(int id);
    public void updateInventory(int id, InventoryDTO dto);
}
