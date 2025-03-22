package com.anshul.expensetrackerapi.controller;

import com.anshul.expensetrackerapi.Entity.Category;
import com.anshul.expensetrackerapi.dto.CategoryDTO;
import com.anshul.expensetrackerapi.io.CategoryRequest;
import com.anshul.expensetrackerapi.io.CategoryResponse;
import com.anshul.expensetrackerapi.mappers.CategoryMapper;
import com.anshul.expensetrackerapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryDTO categoryDTO = categoryMapper.mapToCategoryDTO(categoryRequest);
        categoryDTO = categoryService.saveCategory(categoryDTO);
        return categoryMapper.mapToCategoryResponse(categoryDTO);
    }

    @GetMapping
    public List<CategoryResponse> readCategories() {
        List<CategoryDTO> list = categoryService.getAllCategories();
        return list.stream().map(categoryDTO->categoryMapper.mapToCategoryResponse(categoryDTO)).collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
