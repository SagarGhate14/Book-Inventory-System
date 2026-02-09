package com.cg.dto;

import com.cg.entity.Inventory;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BookDTO {
	
	private int bookId;

	@NotBlank(message = "Book title is required")
    @Size(min = 2, max = 200, message = "Title must be between 2 and 200 characters")
    private String title;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price cannot be negative")
    private double price;

    @NotNull(message = "Please select an author")
    private Integer authorId;
    
    private String authorName;

    @NotNull(message = "Please select a publisher")
    private Integer publisherId;
    
    private String publisherName;

    @NotNull(message = "Please select a category")
    private Integer categoryId;
    
    private String categoryName;

    // Use @Valid to trigger validation inside the Inventory object if needed
    @Valid 
    private Inventory inventory;
    
     // Thymeleaf will bind the ID from the dropdown here
    

    public BookDTO() {
    }

	public BookDTO(int bookId, @NotBlank(message = "Title is required") String title,
			@Min(value = 0, message = "Price cannot be negative") double price,
			@NotNull(message = "Author is required") Integer authorId, String authorName,
			@NotNull(message = "Publisher is required") Integer publisherId, String publisherName,
			@NotNull(message = "Category is required") Integer categoryId, String categoryName, Inventory inventory) {
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
		this.inventory = inventory;
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

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

     

	
}
