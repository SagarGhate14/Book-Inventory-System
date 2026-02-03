package com.cg.service;

import com.cg.entity.Inventory;
import com.cg.dto.InventoryDTO;
import com.cg.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImplementation implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    // 1. Using STREAMS API
    @Override
    public List<InventoryDTO> getAllInventories() {
        return inventoryRepository.findAll().stream()
                .map(inv -> new InventoryDTO(inv.getInventoryId(), inv.getStatus(), inv.getQuantity()))
                .collect(Collectors.toList());
    }

    // 2. Using MANUAL OPTIONAL CHECK with IF-ELSE
    @Override
    public InventoryDTO getInventoryById(int id) {
        Optional<Inventory> optionalInv = inventoryRepository.findById(id);
        
        if (optionalInv.isPresent()) {
            Inventory inv = optionalInv.get();
            return new InventoryDTO(inv.getInventoryId(), inv.getStatus(), inv.getQuantity());
        } else {
            throw new RuntimeException("Inventory not found with id: " + id);
        }
    }

    @Override
    public void saveInventory(InventoryDTO dto) {
        Inventory inv = new Inventory();
        inv.setQuantity(dto.getQuantity());
        inv.setStatus(dto.getQuantity() > 0 ? "Available" : "Out of Stock");
        inventoryRepository.save(inv);
    }

    @Override
    public void updateInventory(int id, InventoryDTO dto) {
        Inventory inv = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        inv.setQuantity(dto.getQuantity());
        inv.setStatus(dto.getQuantity() > 0 ? "Available" : "Out of Stock");
        inventoryRepository.save(inv);
    }

    // 5. Using DIRECT METHOD CALL
    @Override
    public void deleteInventory(int id) {
        inventoryRepository.deleteById(id);
    }
}
