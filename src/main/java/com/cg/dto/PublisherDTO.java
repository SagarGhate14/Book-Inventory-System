package com.cg.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PublisherDTO {
	
    private int publisherId;

    @NotBlank(message = "Publisher name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String publisherName;

    @NotBlank(message = "Address is required")
    @Size(min=4, max=100)
    private String address;

    // Default Constructor
    public PublisherDTO() {
    }

    // Parameterized Constructor
    public PublisherDTO(int publisherId, String publisherName, String address) {
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.address = address;
    }

    // Getters and Setters
    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
