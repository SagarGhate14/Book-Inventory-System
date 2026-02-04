package com.cg.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookDTO {
	
	private int bookId;

    @NotBlank(message = "Title is required")
    private String title;

    @Min(value = 0, message = "Price cannot be negative")
    private double price;

    // We store IDs for selection in the UI (Dropdowns)
    @NotNull(message = "Author is required")
    private Integer authorId;
    private String authorName;
    // Useful for displaying in the List table

    @NotNull(message = "Publisher is required")
    private Integer publisherId;
    private String publisherName;
    

    @NotNull(message = "Category is required")
    private Integer categoryId;
    private String categoryName;
   

    // Inventory quantity is usually managed here during book creation
    private int quantity;
    
     // Thymeleaf will bind the ID from the dropdown here
    

    public BookDTO() {
    }

     

	public BookDTO(int bookId, @NotBlank(message = "Title is required") String title,
			@Min(value = 0, message = "Price cannot be negative") double price,
			@NotNull(message = "Author is required") Integer authorId, String authorName,
			@NotNull(message = "Publisher is required") Integer publisherId, String publisherName,
			@NotNull(message = "Category is required") Integer categoryId, String categoryName, int quantity) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.price = price;
		this.authorId = authorId;
		this.authorName = authorName;
		this.publisherId = publisherId;
		this.publisherName = publisherName;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.quantity = quantity;
	}
	
	
	



	public int getBookId() {
		return bookId;
	}



	public void setBookId(int bookId) {
		this.bookId = bookId;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}



	public Integer getAuthorId() {
		return authorId;
	}



	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}



	public String getAuthorName() {
		return authorName;
	}



	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}



	public Integer getPublisherId() {
		return publisherId;
	}



	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}



	public String getPublisherName() {
		return publisherName;
	}



	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}



	public Integer getCategoryId() {
		return categoryId;
	}



	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}



	public String getCategoryName() {
		return categoryName;
	}



	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}



	public int getQuantity() {
		return quantity;
	}



	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	@Override
	public String toString() {
		return "BookDTO [bookId=" + bookId + ", title=" + title + ", price=" + price + ", authorId=" + authorId
				+ ", authorName="  + ", publisherId=" + publisherId 
				+ ", categoryId=" + categoryId + ", categoryName="  + ", quantity=" + quantity + "]";
	}

}
