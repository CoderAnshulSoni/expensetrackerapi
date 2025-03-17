package com.anshul.expensetrackerapi.service;

import com.anshul.expensetrackerapi.Entity.Category;
import com.anshul.expensetrackerapi.Entity.User;
import com.anshul.expensetrackerapi.dto.CategoryDTO;
import com.anshul.expensetrackerapi.dto.UserDTO;
import com.anshul.expensetrackerapi.exception.ItemAlreadyExistsException;
import com.anshul.expensetrackerapi.exception.ResourceNotFoundException;
import com.anshul.expensetrackerapi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> list = categoryRepository.findByUserId(userService.getLoggedInUser().getId());

        return list.stream().map(this::mapToCategoryDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        boolean isCategoryPresent = categoryRepository.existsByNameAndUserId(categoryDTO.getName(), userService.getLoggedInUser().getId());
        if(isCategoryPresent){
            throw new ItemAlreadyExistsException("Category already exists with the name: " + categoryDTO.getName());
        }
        else
        {
            Category category = mapToCategory(categoryDTO);
            category = categoryRepository.save(category);
            return mapToCategoryDTO(category);
        }
    }

    @Override
    public void deleteCategory(String categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findByUserIdAndCategoryId(userService.getLoggedInUser().getId(), categoryId);
        if(!optionalCategory.isPresent()){
            throw new ResourceNotFoundException("Category not found for the id: " + categoryId);
        }
        categoryRepository.delete(optionalCategory.get());
    }

    private Category mapToCategory(CategoryDTO categoryDTO) {
        return Category.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .categoryIcon(categoryDTO.getCategoryIcon())
                .categoryId(UUID.randomUUID().toString())
                .user(userService.getLoggedInUser())
                .build();
    }

    private CategoryDTO mapToCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .description(category.getDescription())
                .categoryIcon(category.getCategoryIcon())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .user(mapToUserDTO(category.getUser()))
                .build();
    }

    private UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
