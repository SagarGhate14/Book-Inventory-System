package com.cg.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.entity.Publisher;
import com.cg.service.IPublisherService;
import com.cg.service.PublisherService;

@Controller
@RequestMapping("/publishers")
public class PublisherController {
	@Autowired
	private PublisherService publisherService;
	
	@GetMapping("/list")
	public List<Publisher>getAllPublishers(){
		return publisherService.getAllPublishers();
	}

}
