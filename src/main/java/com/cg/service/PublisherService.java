package com.cg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.Publisher;
import com.cg.repository.PublisherRepository;


@Service
public class PublisherService implements IPublisherService{

	@Autowired
	PublisherRepository repo;
	
	@Override
	public List<Publisher> getAllPublishers() {
		return repo.findAll();
	}

	
}
