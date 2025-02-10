package com.anshul.expensetrackerapi.service;

import com.anshul.expensetrackerapi.Entity.Expense;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ExpenseService {

    List<Expense> getAllExpenses();
}
