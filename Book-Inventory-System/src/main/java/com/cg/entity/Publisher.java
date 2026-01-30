package com.cg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "publishers")
public class Publisher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "publisher_id")
	private int publisherId;
	@Column(name = "publisher_name", nullable = false) //nullable = false -> cannot be NULL
	private String publisherName;
	@Column(name = "address")
	private String address;

	public Publisher() {
		super();
	}

	public Publisher(int publisherId, String publisherName, String address) {
		super();
		this.publisherId = publisherId;
		this.publisherName = publisherName;
		this.address = address;
	}

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