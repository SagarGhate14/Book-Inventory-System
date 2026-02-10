package com.cg.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class InventoryDTO {

    private Integer inventoryId;

    @NotBlank(message = "Status cannot be blank")
    @Pattern(
        regexp = "ACTIVE|INACTIVE|OUT_OF_STOCK",
        message = "Status must be one of: ACTIVE, INACTIVE, OUT_OF_STOCK"
    )
    private String status;

    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;

    @NotNull(message = "A book must be associated with this inventory")
    private Integer bookId;
    @NotBlank(message = "Book title cannot be blank")
    private String bookTitle;

    public InventoryDTO() {
    	
    }

    public InventoryDTO(int inventoryId, String status, int quantity) {
        this.inventoryId = inventoryId;
        this.status = status;
        this.quantity = quantity;
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

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
}