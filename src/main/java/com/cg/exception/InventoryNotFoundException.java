package com.cg.exception;

public class InventoryNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public InventoryNotFoundException(int id) {
		super("Inventory record #" + id + " is missing.");
	}

}
