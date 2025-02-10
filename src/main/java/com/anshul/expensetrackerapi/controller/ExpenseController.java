package com.anshul.expensetrackerapi.controller;

import com.anshul.expensetrackerapi.Entity.Expense;
import com.anshul.expensetrackerapi.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService expService;

    @GetMapping("/expenses")
    public List<Expense> getExpenses(){
        return expService.getAllExpenses();
    }

}
