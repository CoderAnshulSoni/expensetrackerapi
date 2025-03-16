package com.anshul.expensetrackerapi.controller;

import com.anshul.expensetrackerapi.Entity.Expense;
import com.anshul.expensetrackerapi.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses")
    public List<Expense> getAllExpenses(Pageable page) {
        return expenseService.getAllExpenses(page).toList();
    }

    @GetMapping("/expenses/{id}")
    public Expense getExpenseById(@PathVariable("id") Long id) {
        return expenseService.getExpenseById(id);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses")
    public void deleteExpenseById(@RequestParam("id") Long id) {
        expenseService.deleteExpenseById(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/expenses")
    public Expense saveExpenseDetails(@Valid @RequestBody Expense expense) {
        return expenseService.saveExpenseDetails(expense);
    }

    @PutMapping("/expenses/{id}")
    public Expense updateExpenseDetails(@PathVariable Long id,@RequestBody Expense expense){
        return expenseService.updateExpenseDetails(id, expense);
    }

    @GetMapping("/expenses/category")
    public List<Expense> getExpensesByCategory(@RequestParam String category, Pageable page) {
        return expenseService.readByCategory(category, page).toList();
    }

    @GetMapping("/expenses/name")
    public List<Expense> getExpensesByName(@RequestParam String keyword, Pageable page) {
        return expenseService.readByName(keyword, page);
    }

    @GetMapping("/expenses/date")
    public List<Expense> getExpensesByDates(@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, Pageable page) {
        return expenseService.readByDate(startDate, endDate, page);
    }
}