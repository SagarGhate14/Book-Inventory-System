package com.cg.service;

import com.cg.entity.Inventory;
import com.cg.exception.GlobalException;
import com.cg.dto.InventoryDTO;
import com.cg.repository.InventoryRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class InventoryService implements IInventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private BookService bookService;

   
    @Override
    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    // 2. Using MANUAL OPTIONAL CHECK with IF-ELSE
    @Override
    public Inventory getInventoryById(int id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new GlobalException.InventoryNotFoundException(id));
    }


    @Override
    public void saveInventory(Inventory inventory) {

        inventoryRepository.save(inventory);
    }

    
    @Override
    @Transactional 
    public void updateInventory(int id, InventoryDTO dto) {
        Inventory inv = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        inv.setQuantity(dto.getQuantity());
        inv.setBook(bookService.findIdByBook(dto.getBookId()));
        inv.setStatus(dto.getQuantity() > 0 ? "AVAILABLE" : "OUT_OF_STOCK");
        inventoryRepository.save(inv);
    }

    // 5. Using DIRECT METHOD CALL
    @Override
    @Transactional
    public void deleteInventory(int id) {
        inventoryRepository.deleteByInventoryId(id);
    }
    
    public Inventory findBookById(Integer bookId) {
    	return inventoryRepository.findByBook_BookId(bookId).orElse(null);
    }
    
    public InventoryDTO toDTO(Inventory entity) {
        if (entity == null) return null;
        
        InventoryDTO dto = new InventoryDTO();
        dto.setInventoryId(entity.getInventoryId());
        dto.setQuantity(entity.getQuantity());
        dto.setStatus(entity.getStatus());
        
        if (entity.getBook() != null) {
            dto.setBookId(entity.getBook().getBookId());
            dto.setBookTitle(entity.getBook().getTitle()); // MAP THE TITLE HERE
        }
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
        List<InventoryDTO> dtoList = new ArrayList<>();
        
        for (Inventory i : entities) {
            InventoryDTO dto = toDTO(i);
            dtoList.add(dto);
        }
        
        return dtoList;
    }

    public List<Inventory> toEntityList(List<InventoryDTO> dtos) {
        List<Inventory> entityList = new ArrayList<>();
        
        for (InventoryDTO d : dtos) {
            Inventory e = toEntity(d);
            entityList.add(e);
        }
        
        return entityList;
    }





}
