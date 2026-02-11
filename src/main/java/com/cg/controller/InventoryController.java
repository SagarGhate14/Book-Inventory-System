package com.cg.controller;

import com.cg.dto.InventoryDTO;

import com.cg.entity.Book;
import com.cg.entity.Inventory;
import com.cg.service.BookService;
import com.cg.service.InventoryService;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private BookService bookService;

    //Get all the inventories
    @GetMapping("/list")
    public String getAllInventories(Model model) {
        // Fetch and convert to DTOs
        List<Inventory> inventoryList = inventoryService.getAllInventories();
        List<InventoryDTO> inventoryDTOList = inventoryService.toDTOList(inventoryList);     
        model.addAttribute("inventories", inventoryDTOList);
        return "inventory/inventory-list";
    }
    
    //Get the new inventory page
    @GetMapping("/add")
    public String showAddForm(Model model) {
    	List<Book> books = bookService.getAllBooks();
        
        model.addAttribute("inventory", new InventoryDTO());
        model.addAttribute("books", books != null ? books : new ArrayList<>()); // Prevent null
        return "inventory/inventory-add";
    }

    //Save the new inventory
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("inventory") InventoryDTO inventoryDTO,BindingResult result,Model model) {
    	 if (result.hasErrors()) {
    	        // Re-populate the books list for the dropdown
    		
    	        model.addAttribute("books", bookService.getAllBooks());
    	        return "inventory/inventory-add"; 
    	    }
    	 
    	int safeQuantity = Math.max(0, inventoryDTO.getQuantity());
        
        Inventory inventory = inventoryService.toEntity(inventoryDTO);
        inventory.setQuantity(safeQuantity);
        
      
        inventory.setStatus(safeQuantity > 0 ? "AVAILABLE" : "OUT_OF_STOCK");


        Book book = bookService.findIdByBook(inventoryDTO.getBookId());
        inventory.setBook(book);

        inventoryService.saveInventory(inventory);
        return "redirect:/inventories/list";
    }

    //Get the edit inventory page
    @GetMapping("/edit/{id}")
    public String update(@PathVariable("id") int id,Model model) {
        Inventory inventory = inventoryService.getInventoryById(id);
        InventoryDTO inventoryDTO = inventoryService.toDTO(inventory);
        model.addAttribute("inventory",inventoryDTO);
        model.addAttribute("books",bookService.getAllBooks());
        return "inventory/inventory-edit";
    }

    //Delete the inventory 
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        inventoryService.deleteInventory(id);
        return "redirect:/inventories/list";
    }
    
    //Update the inventory
    @PutMapping("/update")
    public String updateInventory(@Valid @ModelAttribute("inventoryDTO") InventoryDTO inventoryDTO,BindingResult result, RedirectAttributes redirectAttributes) {
    	 	 
    	Inventory existingWithBook = inventoryService.findBookById(inventoryDTO.getBookId());
    	 
        // Duplicate check
        if (existingWithBook != null && !existingWithBook.getInventoryId().equals(inventoryDTO.getInventoryId())) {
            redirectAttributes.addFlashAttribute("error", "This book is already assigned to another inventory slot.");
            return "redirect:/inventories/list";
        }
 
  //       Update and Save
 
        inventoryService.updateInventory(inventoryDTO.getInventoryId(), inventoryDTO);
        redirectAttributes.addFlashAttribute("success", "Inventory updated successfully!");
        return "redirect:/inventories/list";
    }

}
