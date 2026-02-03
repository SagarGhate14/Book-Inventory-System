package com.cg.service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.cg.entity.Inventory;
import com.cg.exception.InventoryExceptions;
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
    public void deleteInventory(Long id) {
        if (id == null) {
            throw new InventoryExceptions.BadRequest("Id cannot be null");
        }

        if (!inventoryRepository.existsById(id)) {
            throw new InventoryExceptions.NotFound(id);
        }

        inventoryRepository.deleteById(id);
    }

	@Override
	public List<Inventory> getAllInventories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Inventory updateInventory(Long id, Inventory inventory) {
		// TODO Auto-generated method stub
		return null;
	}
}

    
