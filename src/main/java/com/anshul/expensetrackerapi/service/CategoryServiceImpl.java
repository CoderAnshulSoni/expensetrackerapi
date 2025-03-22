package com.anshul.expensetrackerapi.service;

import com.anshul.expensetrackerapi.Entity.Category;
import com.anshul.expensetrackerapi.Entity.User;
import com.anshul.expensetrackerapi.dto.CategoryDTO;
import com.anshul.expensetrackerapi.dto.UserDTO;
import com.anshul.expensetrackerapi.exception.ItemAlreadyExistsException;
import com.anshul.expensetrackerapi.exception.ResourceNotFoundException;
import com.anshul.expensetrackerapi.mappers.CategoryMapper;
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

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> list = categoryRepository.findByUserId(userService.getLoggedInUser().getId());

        return list.stream().map(category -> categoryMapper.mapToCategoryDTO(category)).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        boolean isCategoryPresent = categoryRepository.existsByNameAndUserId(categoryDTO.getName(), userService.getLoggedInUser().getId());
        if(isCategoryPresent){
            throw new ItemAlreadyExistsException("Category already exists with the name: " + categoryDTO.getName());
        }
        else
        {
            Category category = categoryMapper.mapToCategory(categoryDTO);
            category.setCategoryId(UUID.randomUUID().toString());
            category.setUser(userService.getLoggedInUser());
            category = categoryRepository.save(category);
            return categoryMapper.mapToCategoryDTO(category);
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

    private UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
