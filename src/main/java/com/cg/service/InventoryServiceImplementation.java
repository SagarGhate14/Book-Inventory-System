package com.cg.service;

<<<<<<< HEAD
import com.cg.entity.Inventory;
import com.cg.dto.InventoryDTO;
import com.cg.repository.InventoryRepository;
import com.cg.exception.InventoryExceptions;
=======
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> cc9657699ff60ec66886ea69f9f113653e475c69
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
				.map(inv -> new InventoryDTO(inv.getId(), inv.getStatus(), inv.getQuantity()))
				.collect(Collectors.toList());
	}

	@Override
	public InventoryDTO getInventoryById(Long id) {
		Inventory inv = inventoryRepository.findById(id).orElseThrow(() -> new InventoryExceptions.NotFound(id));
		return new InventoryDTO(inv.getId(), inv.getStatus(), inv.getQuantity());
	}

	@Override
	public void saveInventory(InventoryDTO dto) {
		Inventory inv = new Inventory();
		inv.setQuantity(dto.getQuantity());
		// Business logic for status
		inv.setStatus(dto.getQuantity() > 0 ? "Available" : "Out of Stock");
		inventoryRepository.save(inv);
	}

	@Override
	public void updateInventory(Long id, InventoryDTO dto) {
		Inventory inv = inventoryRepository.findById(id).orElseThrow(() -> new InventoryExceptions.NotFound(id));
		inv.setQuantity(dto.getQuantity());
		inv.setStatus(dto.getQuantity() > 0 ? "Available" : "Out of Stock");
		inventoryRepository.save(inv);
	}

<<<<<<< HEAD
	@Override
	public void deleteInventory(Long id) {
		inventoryRepository.deleteById(id);
	}
}
=======
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
>>>>>>> cc9657699ff60ec66886ea69f9f113653e475c69
