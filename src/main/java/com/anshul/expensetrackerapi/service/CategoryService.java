package com.anshul.expensetrackerapi.service;

import com.anshul.expensetrackerapi.dto.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    CategoryDTO saveCategory(CategoryDTO categoryDTO);

    void deleteCategory(String categoryId);
}
