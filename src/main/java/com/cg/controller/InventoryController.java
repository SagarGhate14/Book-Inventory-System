package com.cg.controller;

<<<<<<< HEAD
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.cg.dto.InventoryDTO;
import com.cg.service.InventoryService;

@Controller
@RequestMapping("/inventories")
public class InventoryController {
    @Autowired
    InventoryService inventoryService;

    @GetMapping("/list")
    public String showIndex(Model model) {
        model.addAttribute("inventories", inventoryService.getAllInventories());
        return "inventory"; 
    }

    @GetMapping("/add-form")
    public String showAddForm(Model model) {
        model.addAttribute("inventory", new InventoryDTO());
        return "add"; 
    }

    @GetMapping("/edit-form/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("inventory", inventoryService.getInventoryById(id));
        return "update"; 
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("inventory") InventoryDTO inventoryDTO) {
        inventoryService.saveInventory(inventoryDTO);
        return "redirect:/inventories/list";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("inventory") InventoryDTO inventoryDTO) {
        inventoryService.updateInventory(inventoryDTO.getId(), inventoryDTO);
        return "redirect:/inventories/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return "redirect:/inventories/list";
    }
}
=======
=======
>>>>>>> origin/user
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cg.entity.Inventory;
import com.cg.service.InventoryService;
 
@Controller
@RequestMapping("/inventories")
public class InventoryController {
 
    private final InventoryService inventoryService;
 
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
 
    // SHOW INVENTORY PAGE
    @GetMapping
    public String showInventoryPage(Model model) {
        model.addAttribute("inventory", new Inventory());
        model.addAttribute("inventories", inventoryService.getAllInventories());
        return "inventory"; // inventory.html
    }
 
    // SAVE INVENTORY
    @PostMapping
    public String saveInventory(@ModelAttribute Inventory inventory) {
        inventoryService.saveInventory(inventory);
        return "redirect:/inventories";
    }
 
    // DELETE INVENTORY
    @GetMapping("/delete/{id}")
    public String deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return "redirect:/inventories";
    }
}
<<<<<<< HEAD
 
>>>>>>> origin/author
=======
 
>>>>>>> origin/user
