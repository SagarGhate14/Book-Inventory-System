package com.cg.dto;

public class InventoryDTO {
	private Integer inventoryId;
	private String status;
	private int quantity;
	private Integer bookId;
	 private String bookTitle;

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
	

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public void setInventoryId(Integer inventoryId) {
		this.inventoryId = inventoryId;
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
