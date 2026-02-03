package com.cg.dto;

import java.util.List;

public class CategoryDTO {
	
	private int categoryId;
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
