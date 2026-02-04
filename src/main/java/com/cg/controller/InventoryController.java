package com.cg.controller;

import com.cg.dto.InventoryDTO;
import com.cg.entity.Book;
import com.cg.entity.Inventory;
import com.cg.entity.Status;
import com.cg.service.BookService;
import com.cg.service.IInventoryService;
import com.cg.service.InventoryService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private BookService bookService;

    @GetMapping("/list")
    @Transactional(readOnly = true)
    public String showIndex(Model model) {
        model.addAttribute("inventories", inventoryService.getAllInventories());
        return "inventory/inventory-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
    	List<Book> books = bookService.getAllBooks();
        System.out.println("Books found: " + (books != null ? books.size() : "NULL"));
        
        model.addAttribute("inventory", new InventoryDTO());
        model.addAttribute("books", books != null ? books : new ArrayList<>()); // Prevent null
        return "inventory/inventory-add";
    }

    @GetMapping("/edit-form/{id}")
    public String showUpdateForm(@PathVariable int id, Model model) {
        model.addAttribute("inventory", inventoryService.getInventoryById(id));
        return "inventory/update";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("inventory") InventoryDTO inventoryDTO) {
    	Book book = bookService.findIdByBook(inventoryDTO.getBookId());
    	
    	 if (inventoryDTO.getQuantity() > 0) {
    	        inventoryDTO.setStatus("AVAILABLE");
    	    } else {
    	        inventoryDTO.setStatus("OUT_OF_STOCK");
    	    }

    	    // 2. Pass the DTO to the service
    	    // Your service will handle converting this DTO to an Entity
    	    Inventory inventory = inventoryService.toEntity(inventoryDTO);
       	
       	 
              inventory.setBook(book);
    	    inventoryService.saveInventory(inventory);

    	    // 3. Redirect back to the list
    	    return "redirect:/inventories/list";
    }

    @GetMapping("/edit/{id}")
    public String update(@PathVariable("id") int id,Model model) {
        Inventory inventory = inventoryService.getInventoryById(id);
        model.addAttribute("inventory",inventory);
        model.addAttribute("books",bookService.getAllBooks());
        return "inventory/inventory-edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        inventoryService.deleteInventory(id);
        return "redirect:/inventories/list";
    }
    
}
