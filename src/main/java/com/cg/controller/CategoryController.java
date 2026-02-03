package com.cg.controller;
 
import java.util.List;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cg.dto.CategoryDTO;
import com.cg.entity.Category;
import com.cg.service.CategoryService;
 
@Controller
@RequestMapping("/category")
public class CategoryController {
 
    @Autowired
     CategoryService categoryService;
    
    @GetMapping("/list")
    public String allCategory(Model model) {
    	List<Category> categories = categoryService.getAllCategories();
    	List<CategoryDTO> categoriesDTO = categoryService.toDTOList(categories);
    	model.addAttribute("categories",categoriesDTO);
    	return "Category/category-list";
    }
    
 // Get all categories
    @GetMapping("/new")
    public String getAllCategories(Model model) {
        model.addAttribute("categoryDTO", new CategoryDTO());
        return "Category/category-add";
    }
 
    // Add Category
    @PostMapping("/new")
    public String addCategory(@ModelAttribute CategoryDTO categoryDTO) {
       Category category = categoryService.toEntity(categoryDTO);
       categoryService.addCategory(category);
       return "redirect:/category/list";
        
    }
 
 

// 
    
   @GetMapping("/update/{id}")
   public String updateCategory(@PathVariable int id,Model model) {
	   Category existing = categoryService.getCategoryById(id);
	   CategoryDTO categoryDTO = categoryService.toDTO(existing);
	   model.addAttribute("categoryDTO",categoryDTO);
	   return "category/category-edit";
   }
    // Update category
    @PostMapping("/update")
    public String updateCategory(@ModelAttribute Category category) {
        CategoryDTO existing = categoryService.toDTO(category);
        if (existing != null) {
            existing.setCategoryName(category.getCategoryName());
            categoryService.updateCategory(category);
        }
        return "redirect:/category/list";
    }
// 
    // Delete category
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
         categoryService.deleteCategory(id);
         return "redirect:/category/list";
    }
}