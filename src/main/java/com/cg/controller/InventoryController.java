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
   
    	int safeQuantity = Math.max(0, inventoryDTO.getQuantity());
        
        Inventory inventory = inventoryService.toEntity(inventoryDTO);
        inventory.setQuantity(safeQuantity);
        
        // 2. Set Status using the exact strings the DB expects
        // Note: Use the exact strings found in your 'SHOW CREATE TABLE' command
        inventory.setStatus(safeQuantity > 0 ? "AVAILABLE" : "OUT_OF_STOCK");

        // 3. Link Book
        Book book = bookService.findIdByBook(inventoryDTO.getBookId());
        inventory.setBook(book);

        inventoryService.saveInventory(inventory);
        return "redirect:/inventories/list";
    }

    @GetMapping("/edit/{id}")
    public String update(@PathVariable("id") int id,Model model) {
        Inventory inventory = inventoryService.getInventoryById(id);
        InventoryDTO inventoryDTO = inventoryService.toDTO(inventory);
        model.addAttribute("inventory",inventoryDTO);
        model.addAttribute("books",bookService.getAllBooks());
        return "inventory/inventory-edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        inventoryService.deleteInventory(id);
        return "redirect:/inventories/list";
    }
    
    @PostMapping("/edit")
    public String updateInventory(@ModelAttribute InventoryDTO inventoryDTO) {
    	Inventory currentInventory = inventoryService.getInventoryById(inventoryDTO.getInventoryId());

        if (currentInventory != null) {
            // 2. CHECK: Is the selected bookId already taken by another row?
            // Note: You need a method like findByBookId in your service/repository
            Inventory existingWithBook = inventoryService.findBookById(inventoryDTO.getBookId());

            if (existingWithBook != null && !existingWithBook.getInventoryId().equals(inventoryDTO.getInventoryId())) {
                // This Book ID is already in the database for a different Inventory record!
                // Redirect back with an error message
                return "redirect:/inventories/list";
            }

            // 3. SAFE TO UPDATE: Set the fields from the DTO
            currentInventory.setQuantity(inventoryDTO.getQuantity());
            currentInventory.setStatus(inventoryDTO.getQuantity() > 0 ? "AVAILABLE" : "OUT_OF_STOCK");

            // 4. Link the Book
            Book book = bookService.findIdByBook(inventoryDTO.getBookId());
            currentInventory.setBook(book);

            // 5. Save (Hibernate will perform an UPDATE because currentInventory is managed)
            inventoryService.saveInventory(currentInventory);
        }
        
        return "redirect:/inventories/list";
    }
    
}
