package com.cg.dto;

public class InventoryDTO {
	private int inventoryId;
	private String status;
	private int quantity;
	private Integer bookId;

	

	public InventoryDTO() {
	}

	public InventoryDTO(int inventoryId, String status, int quantity) {
		this.inventoryId = inventoryId;
		this.status = status;
		this.quantity = quantity;
	}

	public int getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
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

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	@Override
	public String toString() {
		return "InventoryDTO [inventoryId=" + inventoryId + ", status=" + status + ", quantity=" + quantity
				+ ", bookId=" + bookId + "]";
	}

		
}
