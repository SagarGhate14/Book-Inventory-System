package com.cg.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CategoryDTO {
	
	private int categoryId;
	
	@NotBlank(message = "Category name cannot be empty")
    @Size(min = 3, max = 50, message = "Category name must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s&-]+$", message = "Category name can only contain letters, spaces, '&', and '-'")
    private String categoryName;
    
    // Optional: Only include book IDs or titles if needed for the UI, 
    // but usually, a simple list of names is best for a DTO.
    private List<String> bookTitles;

    // Default Constructor
    public CategoryDTO() {
    }

    // Constructor for easy mapping
    public CategoryDTO(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Getters and Setters
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getBookTitles() {
        return bookTitles;
    }

    public void setBookTitles(List<String> bookTitles) {
        this.bookTitles = bookTitles;
    }

}
