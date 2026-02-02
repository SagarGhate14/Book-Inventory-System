package com.cg.dto;

public class InventoryDTO {
	private Long id;
	private String status;
	private int quantity;

	public InventoryDTO() {
	}

	public InventoryDTO(Long id, String status, int quantity) {
		this.id = id;
		this.status = status;
		this.quantity = quantity;
	}

	// Getters and Setters must match the Entity exactly for Thymeleaf
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
