package com.cg.service;

import com.cg.entity.Inventory;
import com.cg.dto.InventoryDTO;
import com.cg.repository.InventoryRepository;
import com.cg.exception.InventoryExceptions;
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

	@Override
	public void deleteInventory(Long id) {
		inventoryRepository.deleteById(id);
	}
}
