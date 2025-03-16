package com.anshul.expensetrackerapi.service;

import com.anshul.expensetrackerapi.Entity.Expense;
import com.anshul.expensetrackerapi.exception.ResourceNotFoundException;
import com.anshul.expensetrackerapi.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    @Autowired
    private UserService userService;

    @Autowired
    private ExpenseRepository expenseRepo;

    @Override
    public Page<Expense> getAllExpenses(Pageable page) {
        return expenseRepo.findByUserId(userService.getLoggedInUser().getId(), page);
    }

    @Override
    public Expense getExpenseById(Long id) {
        Optional<Expense> expense = expenseRepo.findByUserIdAndId(userService.getLoggedInUser().getId(), id);

        if(expense.isPresent()){
            return expense.get();
        }
        throw new ResourceNotFoundException("Expense is not found for the Id" + id);
    }

    @Override
    public void deleteExpenseById(Long id){
        Expense expense = getExpenseById(id);
        expenseRepo.delete(expense);
    }

    @Override
    public Expense saveExpenseDetails(Expense expense){
        expense.setUser(userService.getLoggedInUser());
        return expenseRepo.save(expense);
    }

    @Override
    public Expense updateExpenseDetails(Long id, Expense expense){

        Expense oldExpense = getExpenseById(id);

        oldExpense.setName(expense.getName() != null ? expense.getName() : oldExpense.getName());
        oldExpense.setDescription(expense.getDescription() != null ? expense.getDescription() : oldExpense.getDescription());
        oldExpense.setCategory(expense.getCategory() != null ? expense.getCategory() : oldExpense.getCategory());
        oldExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : oldExpense.getAmount());
        oldExpense.setDate(expense.getDate() != null ? expense.getDate() : oldExpense.getDate());

        return expenseRepo.save(oldExpense);
    }

    @Override
    public Page<Expense> readByCategory(String category, Pageable page) {
        return expenseRepo.findByUserIdAndCategory(userService.getLoggedInUser().getId(),category, page);
    }

    @Override
    public List<Expense> readByName(String keyword, Pageable page) {
        return expenseRepo.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(),keyword, page).toList();
    }

    @Override
    public List<Expense> readByDate(Date startDate, Date endDaTe, Pageable page) {

        if(startDate == null){
            startDate = new Date(0);
        }
        if(endDaTe == null){
            endDaTe = new Date(System.currentTimeMillis());
        }
        Page<Expense> pages = expenseRepo.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(), startDate, endDaTe, page);

        return pages.toList();
    }
}
