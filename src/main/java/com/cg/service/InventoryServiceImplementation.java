package com.cg.service;

import com.cg.entity.Inventory;
import com.cg.dto.InventoryDTO;
import com.cg.repository.InventoryRepository;

// ✅ Using static inner exceptions from GlobalException
import static com.cg.exception.GlobalException.InventoryNotFoundException;
import static com.cg.exception.GlobalException.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImplementation implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public List<InventoryDTO> getAllInventories() {
        return inventoryRepository.findAll().stream()
                .map(inv -> new InventoryDTO(inv.getInventoryId(), inv.getStatus(), inv.getQuantity()))
                .collect(Collectors.toList());
    }

    @Override
    public InventoryDTO getInventoryById(int id) {
        Inventory inv = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException(id));
        return new InventoryDTO(inv.getInventoryId(), inv.getStatus(), inv.getQuantity());
    }

    @Override
    public void saveInventory(InventoryDTO dto) {
        if (dto == null) {
            throw new BadRequestException("Inventory details are required.");
        }
        Inventory inv = new Inventory();
        inv.setQuantity(dto.getQuantity());
        // Status logic: if quantity > 0 then Available, else Out of Stock
        inv.setStatus(dto.getQuantity() > 0 ? "Available" : "Out of Stock");
        inventoryRepository.save(inv);
    }

    @Override
    public void updateInventory(int id, InventoryDTO dto) {
        if (dto == null) {
            throw new BadRequestException("Inventory update body is required.");
        }
        
        // Find existing record or throw 404
        Inventory inv = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException(id));

        inv.setQuantity(dto.getQuantity());
        inv.setStatus(dto.getQuantity() > 0 ? "Available" : "Out of Stock");
        inventoryRepository.save(inv);
    }

    @Override
    public void deleteInventory(int id) {
        // ✅ Check if exists before deleting to trigger your custom 404 error page
        if (!inventoryRepository.existsById(id)) {
            throw new InventoryNotFoundException(id);
        }
        inventoryRepository.deleteById(id);
    }
}
