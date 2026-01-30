package com.cg.service;

import org.springframework.stereotype.Service;

import com.cg.entity.Inventory;
import com.cg.repository.InventoryRepository;

import java.util.List;
 
@Service
public class InventoryServiceImplementation implements InventoryService {
 
    private final InventoryRepository inventoryRepository;
 
    public InventoryServiceImplementation(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
 
    @Override
    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }
 
    @Override
    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id).orElse(null);
    }
 
    @Override
    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }
 
    @Override
    public Inventory updateInventory(Long id, Inventory inventory) {
        Inventory existing = inventoryRepository.findById(id).orElse(null);
 
        if (existing != null) {
            existing.setQuantity(inventory.getQuantity());
            existing.setStatus(inventory.getStatus());
            existing.setBook(inventory.getBook());
            existing.setUser(inventory.getUser());
            return inventoryRepository.save(existing);
        }
        return null;
    }
 
    @Override
    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }
}
 