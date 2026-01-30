package com.cg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cg.entity.Category;
import com.cg.repository.CategoryRepository;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
     CategoryRepository categoryRepository;

    // Add Category
    @PostMapping("/add")
    public Category addCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    // Get all categories
    @GetMapping("/add")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Get category by id
    @GetMapping("/get/{id}")
    public Category getCategoryById(@PathVariable int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    // Update category
    @PutMapping("/update/{id}")
    public Category updateCategory(@PathVariable int id,
                                   @RequestBody Category category) {
        Category existing = categoryRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setCategoryName(category.getCategoryName());
            return categoryRepository.save(existing);
        }
        return null;
    }

    // Delete category
    @DeleteMapping("/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        categoryRepository.deleteById(id);
        return "Category deleted successfully";
    }
}