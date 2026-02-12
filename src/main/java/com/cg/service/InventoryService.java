package com.cg.service;

import com.cg.entity.Book;
import com.cg.entity.Inventory;
import com.cg.exception.GlobalException;
import com.cg.exception.InventoryNotFoundException;
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

   
    //Get all the inventories
    @Override
    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    // Get the inventory by Id
    @Override
    public Inventory getInventoryById(int id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException(id));
    }


    //Save the inventory in db
    @Override
    public void saveInventory(Inventory inventory) {
    	Book book = bookService.findIdByBook(inventory.getBook().getBookId());
               inventory.setBook(book);
        inventoryRepository.save(inventory);
    }

    //update the inventory
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

    // Using DIRECT METHOD CALL , Delete By Id
    @Override
    @Transactional
    public void deleteInventory(int id) {
        inventoryRepository.deleteByInventoryId(id);
    }
    
    //Find the book by Id
    public Inventory findBookById(Integer bookId) {
    	return inventoryRepository.findByBook_BookId(bookId).orElse(null);
    }
    
    //Converting entity to DTO
    public InventoryDTO toDTO(Inventory entity) {
        if (entity == null) return null;
        
        InventoryDTO dto = new InventoryDTO();
        dto.setInventoryId(entity.getInventoryId());
        dto.setQuantity(entity.getQuantity());
        dto.setStatus(entity.getStatus());
        
        if (entity.getBook() != null) {
            dto.setBookId(entity.getBook().getBookId());
            dto.setBookTitle(entity.getBook().getTitle()); 
        }
        return dto;
    }

    //Converting DTO to entity
    public Inventory toEntity(InventoryDTO dto) {
        if (dto == null) return null;
        
        Inventory entity = new Inventory();
        int quantity =dto.getQuantity();
        entity.setStatus(quantity > 0 ? "AVAILABLE" : "OUT_OF_STOCK");
        entity.setQuantity(dto.getQuantity());
        Book book = new Book();
        book.setBookId(dto.getBookId()); 
        entity.setBook(book);
        
        return entity;
    }
    
    //Converting entityList to DTOList
    public List<InventoryDTO> toDTOList(List<Inventory> entities) {
        List<InventoryDTO> dtoList = new ArrayList<>();
        
        for (Inventory i : entities) {
            InventoryDTO dto = toDTO(i);
            dtoList.add(dto);
        }
        
        return dtoList;
    }


}
