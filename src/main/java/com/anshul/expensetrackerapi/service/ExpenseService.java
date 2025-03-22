package com.anshul.expensetrackerapi.service;

import com.anshul.expensetrackerapi.Entity.Expense;
import com.anshul.expensetrackerapi.dto.ExpenseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public interface ExpenseService {

    List<ExpenseDTO> getAllExpenses(Pageable page);

    ExpenseDTO getExpenseById(String expenseId);

    void deleteExpenseById(String expenseId);

    ExpenseDTO saveExpenseDetails(ExpenseDTO expense);

    ExpenseDTO updateExpenseDetails(String expenseId, ExpenseDTO expenseDTO);

    List<ExpenseDTO> readByCategory(String category, Pageable page);

    List<ExpenseDTO> readByName(String keyword, Pageable page);

    List<ExpenseDTO> readByDate(Date startDate, Date endDaTe, Pageable page);

}
