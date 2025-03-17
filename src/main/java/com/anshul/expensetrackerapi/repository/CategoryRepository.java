package com.anshul.expensetrackerapi.repository;

import com.anshul.expensetrackerapi.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUserId(Long userID);

    Optional<Category> findByUserIdAndCategoryId(Long userID, String categoryId);

    boolean existsByNameAndUserId(String name, Long userId);

    Optional<Category> findByNameAndUserId(String name, Long userId);
}
