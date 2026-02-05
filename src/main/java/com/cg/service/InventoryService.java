package com.cg.service;

import com.cg.entity.Inventory;
import com.cg.entity.Status;
import com.cg.dto.InventoryDTO;
import com.cg.repository.InventoryRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService implements IInventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

   
    @Override
    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    // 2. Using MANUAL OPTIONAL CHECK with IF-ELSE
    @Override
    public Inventory getInventoryById(int id) {
        Optional<Inventory> optionalInv = inventoryRepository.findById(id);
        
//        if (optionalInv.isPresent()) {
//            Inventory inv = optionalInv.get();
//            return new InventoryDTO(inv.getInventoryId(), inv.getStatus(), inv.getQuantity());
//        } else {
//            throw new RuntimeException("Inventory not found with id: " + id);
//        }
        return optionalInv.get();
    }

    @Override
    public void saveInventory(Inventory inventory) {
       
       // inv.setStatus(dto.getQuantity() > 0 ? "Available" : "Out of Stock");
        inventoryRepository.save(inventory);
    }

    @Override
    @Transactional 
    public void updateInventory(int id, InventoryDTO dto) {
        Inventory inv = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        inv.setQuantity(dto.getQuantity());
       // inv.setStatus(dto.getQuantity() > 0 ? "Available" : "Out of Stock");
        inventoryRepository.save(inv);
    }

    // 5. Using DIRECT METHOD CALL
    @Override
    @Transactional
    public void deleteInventory(int id) {
        inventoryRepository.deleteByInventoryId(id);
    }
    
    public Inventory findBookById(Integer bookId) {
    	return inventoryRepository.findByBookId(bookId).get();
    }
    
    public InventoryDTO toDTO(Inventory entity) {
        if (entity == null) return null;
        
        InventoryDTO dto = new InventoryDTO();
        dto.setInventoryId(entity.getInventoryId());
        dto.setQuantity(entity.getQuantity());
        // Handling Enum to String conversion
        dto.setStatus(entity.getStatus());
        
        if (entity.getBook() != null) {
            dto.setBookId(entity.getBook().getBookId());
        }
        
        // Note: You might want to add bookTitle or userId to DTO 
        // to make the UI more useful.
        return dto;
    }
    
    public Inventory toEntity(InventoryDTO dto) {
        if (dto == null) return null;
        
        Inventory entity = new Inventory();
       // entity.setInventoryId(dto.getInventoryId());
        entity.setQuantity(dto.getQuantity());
        
        // Handling String to Enum conversion
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus().toUpperCase());
        }
        
        // Relationship fields (Book/User) are usually handled during 
        // the Save process in the Controller/Service
        return entity;
    }
    
    public List<InventoryDTO> toDTOList(List<Inventory> entities) {
        return entities.stream()
                       .map(this::toDTO)
                       .collect(Collectors.toList());
    }
    
    public List<Inventory> toEntityList(List<InventoryDTO> dtos) {
        return dtos.stream()
                   .map(this::toEntity)
                   .collect(Collectors.toList());
    }

	@Override
	public Inventory findById(Integer id) {
		return inventoryRepository.findById(id).get();
	}
}
