package com.anshul.expensetrackerapi.controller;

import com.anshul.expensetrackerapi.Entity.Expense;
import com.anshul.expensetrackerapi.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

//     @GetMapping("/expenses")
//     public Page<Expense> getAllExpenses(Pageable page) {
//         return expenseService.getAllExpenses(page);
//     }

    //{{url}}/expenses?size=2&page=0
    //{{url}}/expenses?sort=amount,desc
    //{{url}}/expenses?size=2&page=0&sort=amount
    @GetMapping("/expenses")
    public List<Expense> getAllExpenses(Pageable page) {
        return expenseService.getAllExpenses(page).toList();
    }

    //{{url}}/expenses/1

    @GetMapping("/expenses/{id}")
    public Expense getExpenseById(@PathVariable("id") Long id) {
        return expenseService.getExpenseById(id);
    }

    //{{url}}/expenses?id=1

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
}


// {
//   "name":"Internet Bill",
//   "amount":700.00,
//   "category":"Bills",
//   "description":"internet bills".
//   "date":"2021-10-15"
// }