package com.anshul.expensetrackerapi.repository;

import com.anshul.expensetrackerapi.Entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Page<Expense> findByUserIdAndCategory(Long userId, String category, Pageable pageable);

    Page<Expense> findByUserIdAndCategoryId(Long userId, Long categoryId, Pageable pageable);

    Page<Expense> findByUserIdAndNameContaining(Long userId, String keyword, Pageable pageable);

    List<Expense> findByUserIdAndDateBetween(Long userId, Date startDate, Date endDate, Pageable pageable);

    Page<Expense> findByUserId(Long userId, Pageable page);

    Optional<Expense> findByUserIdAndExpenseId(Long userId, String expenseId);

}
