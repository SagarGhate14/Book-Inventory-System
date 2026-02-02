package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.cg.entity.Inventory;
import com.cg.service.InventoryService;
@Controller
@RequestMapping("/inventories")
public class InventoryController {
    @Autowired
	InventoryService inventoryService;

    // LIST VIEW (Main Page)
    @GetMapping("/list")
    public String showIndex(Model model) {
        model.addAttribute("inventories", inventoryService.getAllInventories());
        return "inventory"; 
    }

    // SHOW ADD PAGE
    @GetMapping("/add-form")
    public String showAddForm(Model model) {
        model.addAttribute("inventory", new Inventory());
        return "add"; 
    }

    // SHOW UPDATE PAGE
    @GetMapping("/edit-form/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("inventory", inventoryService.getInventoryById(id));
        return "update"; 
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Inventory inventory) {
        inventoryService.saveInventory(inventory);
        return "redirect:/inventories/list";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Inventory inventory) {
        inventoryService.updateInventory(inventory.getId(), inventory);
        return "redirect:/inventories/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return "redirect:/inventories/list";
    }
}
