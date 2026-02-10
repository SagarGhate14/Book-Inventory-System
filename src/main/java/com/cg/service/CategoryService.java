package com.cg.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cg.dto.CategoryDTO;
import com.cg.entity.Category;
import com.cg.exception.GlobalException;
import com.cg.repository.CategoryRepository;

// ✅ Static imports from your GlobalException file
import static com.cg.exception.GlobalException.CategoryNotFoundException;
import static com.cg.exception.GlobalException.BadRequestException;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Override
    public Category addCategory(Category category) {
        if (category == null) {
            throw new BadRequestException("Category details are required.");
        }
        return categoryRepo.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryRepo.findById(id)
                .orElseThrow(() -> new GlobalException.CategoryNotFoundException(id));
    }


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

    @Override
    public void deleteCategory(int id) {
        if (!categoryRepo.existsById(id)) {
            throw new CategoryNotFoundException(id);
        }
        categoryRepo.deleteById(id);
    }

    /* --- MAPPING HELPERS (Logic Unchanged) --- */

    public CategoryDTO toDTO(Category category) {
        if (category == null) return null;
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        return dto;
    }

    public Category toEntity(CategoryDTO dto) {
        if (dto == null) return null;
        Category entity = new Category();
        entity.setCategoryId(dto.getCategoryId());
        entity.setCategoryName(dto.getCategoryName());
        return entity;
    }

    public List<CategoryDTO> toDTOList(List<Category> categories) {
        List<CategoryDTO> dtoList = new ArrayList<>();
        if (categories == null) return dtoList;
        for (Category category : categories) {
            dtoList.add(toDTO(category));
        }
        return dtoList;
    }
}
