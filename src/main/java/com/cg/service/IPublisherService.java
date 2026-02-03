package com.cg.service;

import java.util.List;

import com.cg.dto.PublisherDTO;
import com.cg.entity.Publisher;

public interface IPublisherService {
	public List<Publisher> getAllPublishers();
	public Publisher savePublisher(Publisher publisher);
	public Publisher updatePublisher(Publisher publisher);
	public void deletePublisher(int pId);
	public Publisher findById(int pId);

}
