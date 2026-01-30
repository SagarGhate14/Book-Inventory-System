package com.cg.service;

import java.util.List;

import com.cg.entity.Inventory;

public interface InventoryService {

	public Inventory saveInventory(Inventory inventory);

	public Inventory getInventoryById(Long id);

	public List<Inventory> getAllInventories();

	public Inventory updateInventory(Long id, Inventory inventory);

	public void deleteInventory(Long id);
}
