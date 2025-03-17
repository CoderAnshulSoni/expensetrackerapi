package com.anshul.expensetrackerapi.controller;

import com.anshul.expensetrackerapi.Entity.Category;
import com.anshul.expensetrackerapi.dto.CategoryDTO;
import com.anshul.expensetrackerapi.io.CategoryRequest;
import com.anshul.expensetrackerapi.io.CategoryResponse;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryDTO categoryDTO = mapToDTO(categoryRequest);
        categoryDTO = categoryService.saveCategory(categoryDTO);
        return mapToResponse(categoryDTO);
    }

    @GetMapping
    public List<CategoryResponse> readCategories() {
        List<CategoryDTO> list = categoryService.getAllCategories();
        return list.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
    }

    private CategoryResponse mapToResponse(CategoryDTO categoryDTO) {
        return CategoryResponse.builder()
                .categoryId(categoryDTO.getCategoryId())
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .categoryIcon(categoryDTO.getCategoryIcon())
                .createdAt(categoryDTO.getCreatedAt())
                .updatedAt(categoryDTO.getUpdatedAt())
                .build();
    }

    private CategoryDTO mapToDTO(CategoryRequest categoryRequest){
        return CategoryDTO.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .categoryIcon(categoryRequest.getIcon())
                .build();
    }
}
