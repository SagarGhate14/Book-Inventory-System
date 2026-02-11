package com.cg.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.PublisherDTO;
import com.cg.entity.Publisher;
import com.cg.service.IPublisherService;
import com.cg.service.PublisherService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/publishers")
public class PublisherController {
	@Autowired
	private PublisherService publisherService;
	
	
	//Get all the publishers
	@GetMapping("/list")
	public String getAllPublishers(Model model){
       List<Publisher> publishers = publisherService.getAllPublishers();
       List<PublisherDTO> publisherDTO = publisherService.toDTOList(publishers);
       model.addAttribute("publisherDTO",publisherDTO);
       return "publisher/publisher-list";
		
	}
	
	//Get the new publisher page
	@GetMapping("/new")
	public String addPublisher(Model model) {
		 model.addAttribute("publisherDTO",new PublisherDTO());
		 return "publisher/publisher-add";
	}
      
	//Save the new publisher
	@PostMapping("/add")
	public String savePublisher(@Valid @ModelAttribute PublisherDTO publisherDTO,BindingResult result) {
		if (result.hasErrors()) {
	        // 2. If errors exist, stop and return the EDIT form
	        return "publisher/publisher-add"; 
	    }
		
		
		Publisher publisher = publisherService.toEntity(publisherDTO);
		publisherService.savePublisher(publisher);
		return "redirect:/publishers/list";
	}
	
	//Get the edit publisher page
	@GetMapping("/update/{id}")
	public String updatePublisher(Model model,@PathVariable("id") int pId) {
		Publisher publisher = publisherService.findById(pId);
		PublisherDTO publisherDTO = publisherService.toDTO(publisher);
		model.addAttribute("publisherDTO",publisherDTO);
		return "publisher/publisher-edit";
	}
	
	//Update the publisher 
	@PutMapping("/update")
	public String updatedPublisher(@Valid @ModelAttribute PublisherDTO publisherDTO,BindingResult result) {
		if (result.hasErrors()) {
	        // 2. If errors exist, stop and return the EDIT form
	        return "publisher/publisher-edit"; 
	    }
		
		Publisher publisher = publisherService.toEntity(publisherDTO);
		publisherService.updatePublisher(publisher);
		return "redirect:/publishers/list";
	}
	
	//Delete the publisher
	@DeleteMapping("/delete/{id}")
	public String deletePublisher(@PathVariable("id") int pId) {
		publisherService.deletePublisher(pId);
		return "redirect:/publishers/list";
	}

}
