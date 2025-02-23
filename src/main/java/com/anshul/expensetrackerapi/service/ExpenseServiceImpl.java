package com.anshul.expensetrackerapi.service;

import com.anshul.expensetrackerapi.Entity.Expense;
import com.anshul.expensetrackerapi.exception.ResourceNotFoundException;
import com.anshul.expensetrackerapi.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    @Autowired
    private ExpenseRepository expenseRepo;

    @Override
    public Page<Expense> getAllExpenses(Pageable page) {
        return expenseRepo.findAll(page);
    }

    @Override
    public Expense getExpenseById(Long id) {
        Optional<Expense> expense = expenseRepo.findById(id);

        if(expense.isPresent()){
            return expense.get();
        }

        // throw new RuntimeException("Expense is not found for the Id" + id);
        throw new ResourceNotFoundException("Expense is not found for the Id" + id);
    }

    @Override
    public void deleteExpenseById(Long id){
        expenseRepo.deleteById(id);
    }

    @Override
    public Expense saveExpenseDetails(Expense expense){
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
}
