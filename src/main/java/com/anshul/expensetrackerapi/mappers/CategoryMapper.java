package com.anshul.expensetrackerapi.mappers;

import com.anshul.expensetrackerapi.Entity.Category;
import com.anshul.expensetrackerapi.dto.CategoryDTO;
import com.anshul.expensetrackerapi.io.CategoryRequest;
import com.anshul.expensetrackerapi.io.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper{

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category mapToCategory(CategoryDTO  categoryDTO);
    CategoryDTO mapToCategoryDTO(Category category);
    @Mapping(target = "categoryIcon", source = "icon")
    CategoryDTO mapToCategoryDTO(CategoryRequest categoryRequest);
    CategoryResponse mapToCategoryResponse(CategoryDTO categoryDTO);
}
