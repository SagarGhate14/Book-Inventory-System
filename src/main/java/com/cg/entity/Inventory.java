package com.cg.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Inventory")
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer inventoryId;

	@Column
	private String status; // Changed from enum to String

	@Column
	private int quantity;

	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "book_id")
	private Book book;


	public Inventory() {
	}


	public Inventory(Integer inventoryId, String status, int quantity, Book book) {
		super();
		this.inventoryId = inventoryId;
		this.status = status;
		this.quantity = quantity;
		this.book = book;
	}


	public Integer getInventoryId() {
		return inventoryId;
	}


	public void setInventoryId(Integer inventoryId) {
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


	public Book getBook() {
		return book;
	}


	public void setBook(Book book) {
		this.book = book;
	}

	
	


	
	
	
}
