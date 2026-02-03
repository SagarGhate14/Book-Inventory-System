package com.cg.service;


import java.util.ArrayList;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dto.CategoryDTO;
import com.cg.entity.Category;
import com.cg.repository.CategoryRepository;
 
@Service
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
	public Category updateCategory(Category category) {
		return categoryRepo.save(category);
	}
 
	@Override
	public void deleteCategory(int id) {
		 categoryRepo.deleteById(id);
	}
	
	
	 public CategoryDTO toDTO(Category category) {
	        if (category == null) {
	            return null;
	        }
	        CategoryDTO dto = new CategoryDTO();
	        dto.setCategoryId(category.getCategoryId());
	        dto.setCategoryName(category.getCategoryName());
	        return dto;
	    }
	 
	 public Category toEntity(CategoryDTO dto) {
	        if (dto == null) {
	            return null;
	        }
	        Category entity = new Category();
	        entity.setCategoryId(dto.getCategoryId());
	        entity.setCategoryName(dto.getCategoryName());
	        // Note: 'books' list is usually handled by the Service layer during updates
	        return entity;
	    }
	 
	 
	 public  List<CategoryDTO> toDTOList(List<Category> categories) {
	        List<CategoryDTO> dtoList = new ArrayList<>();
	        
	        if (categories == null) {
	            return dtoList;
	        }

	        for (Category category : categories) {
	            // Reusing your existing toDTO logic
	            CategoryDTO dto = toDTO(category);
	            dtoList.add(dto);
	        }
	        
	        return dtoList;
	    }
 
}
