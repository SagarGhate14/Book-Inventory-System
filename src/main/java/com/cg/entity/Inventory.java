package com.cg.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Inventory")
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int inventoryId;

	@Column
	private Status status; // Changed from enum to String

	@Column
	private int quantity;

	@OneToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "book_id")
	private Book book;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Inventory() {
	}

	
	public Inventory(int inventoryId, Status status, int quantity, Book book, User user) {
		super();
		this.inventoryId = inventoryId;
		this.status = status;
		this.quantity = quantity;
		this.book = book;
		this.user = user;
	}

	

	public int getInventoryId() {
		return inventoryId;
	}


	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
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


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public String toString() {
		return "Inventory [inventoryId=" + inventoryId + ", status=" + status + ", quantity=" + quantity + ", book="
				+ book + ", user=" + user + "]";
	}
	
	
	
}
