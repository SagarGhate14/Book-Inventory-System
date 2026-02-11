package com.cg.service;

import java.util.ArrayList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cg.dto.CategoryDTO;
import com.cg.entity.Category;
import com.cg.exception.BadRequestException;
import com.cg.exception.CategoryNotFoundException;
import com.cg.exception.GlobalException;
import com.cg.repository.CategoryRepository;



@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    
    //Save the new category in db
    @Override
    public Category addCategory(Category category) {
        if (category == null) {
            throw new BadRequestException("Category details are required.");
        }
        return categoryRepo.save(category);
    }

    //Get all the categories from db
    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    //find the category by Id
    @Override
    public Category getCategoryById(int id) {
        return categoryRepo.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

      //update the category
    @Override
    public Category updateCategory(Category category) {
        if (category == null) {
            throw new BadRequestException("Category update body is required.");
        }
        if (!categoryRepo.existsById(category.getCategoryId())) {
            throw new CategoryNotFoundException(category.getCategoryId());
        }
        return categoryRepo.save(category);
    }

    //Delete the category by Id
    @Override
    public void deleteCategory(int id) {
        if (!categoryRepo.existsById(id)) {
            throw new CategoryNotFoundException(id);
        }
        categoryRepo.deleteById(id);
    }

  
      //Converting entity to DTO
    public CategoryDTO toDTO(Category category) {
        if (category == null) return null;
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        return dto;
    }

    //Converting DTO to entity
    public Category toEntity(CategoryDTO dto) {
        if (dto == null) return null;
        Category entity = new Category();
        entity.setCategoryId(dto.getCategoryId());
        entity.setCategoryName(dto.getCategoryName());
        return entity;
    }

    //Converting entityList to DTOList
    public List<CategoryDTO> toDTOList(List<Category> categories) {
        List<CategoryDTO> dtoList = new ArrayList<>();
        if (categories == null) return dtoList;
        for (Category category : categories) {
            dtoList.add(toDTO(category));
        }
        return dtoList;
    }
}
