package com.cg.service;
import java.util.List;
import com.cg.entity.Category;

public interface ICategoryService {
	Category addCategory(Category category);
	List<Category> getAllCategories();
	Category getCategoryById(int id);
	Category updateCategory(int id, Category category);
	void deleteCategory(int id);
}


