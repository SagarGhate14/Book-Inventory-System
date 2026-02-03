package com.cg.service;


import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
 
import com.cg.entity.Category;
import com.cg.repository.CategoryRepository;
 
public class CategoryService implements ICategoryService{
	@Autowired
	CategoryRepository categoryRepo;
 
	@Override
	public Category addCategory(Category category) {
		return categoryRepo.save(category);
	}
 
	@Override
	public List<Category> getAllCategories() {
		return categoryRepo.findAll();
	}
 
	@Override
	public Category getCategoryById(int id) {
		return categoryRepo.findById(id).get();
	}
 
	@Override
	public Category updateCategory(int id, Category category) {
		return categoryRepo.save(category);
	}
 
	@Override
	public void deleteCategory(int id) {
		 categoryRepo.deleteById(id);
	}
 
}
