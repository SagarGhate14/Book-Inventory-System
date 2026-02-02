package com.cg.service;

<<<<<<< HEAD
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.Inventory;
import com.cg.exception.InventoryExceptions;
import com.cg.repository.InventoryRepository;

import java.util.List;

@Service
public class InventoryServiceImplementation implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    // Set status based on quantity
    private void updateStatus(Inventory inventory) {
        if (inventory.getQuantity() > 0) {
            inventory.setStatus("Available");
        } else {
            inventory.setStatus("Out of Stock");
        }
    }

    // Validate inventory data
    private void validateInventory(Inventory inventory) {
        if (inventory == null) {
            throw new InventoryExceptions.BadRequest("Inventory cannot be null");
        }
        if (inventory.getQuantity() < 0) {
            throw new InventoryExceptions.BadRequest("Quantity cannot be negative");
        }
    }

    @Override
    public Inventory saveInventory(Inventory inventory) {
        validateInventory(inventory);
        updateStatus(inventory);
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory updateInventory(Long id, Inventory inventory) {
        if (id == null) {
            throw new InventoryExceptions.BadRequest("Id cannot be null");
        }
        validateInventory(inventory);

        Inventory existing = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryExceptions.NotFound(id));

        existing.setQuantity(inventory.getQuantity());
        existing.setBook(inventory.getBook());
        existing.setUser(inventory.getUser());

        updateStatus(existing);

        return inventoryRepository.save(existing);
    }

    @Override
    public Inventory getInventoryById(Long id) {
        if (id == null) {
            throw new InventoryExceptions.BadRequest("Id cannot be null");
        }
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryExceptions.NotFound(id));
    }

=======
=======
>>>>>>> origin/user
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
 
<<<<<<< HEAD
>>>>>>> origin/author
=======
>>>>>>> origin/user
    @Override
    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }
<<<<<<< HEAD
<<<<<<< HEAD

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
}
=======
=======
>>>>>>> origin/user
 
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
<<<<<<< HEAD
 
>>>>>>> origin/author
=======
 
>>>>>>> origin/user
