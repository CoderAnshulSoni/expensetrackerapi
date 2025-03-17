package com.anshul.expensetrackerapi.controller;

import com.anshul.expensetrackerapi.Entity.Expense;
import com.anshul.expensetrackerapi.dto.CategoryDTO;
import com.anshul.expensetrackerapi.dto.ExpenseDTO;
import com.anshul.expensetrackerapi.io.CategoryResponse;
import com.anshul.expensetrackerapi.io.ExpenseRequest;
import com.anshul.expensetrackerapi.io.ExpenseResponse;
import com.anshul.expensetrackerapi.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses")
    public List<ExpenseResponse> getAllExpenses(Pageable page) {
        List<ExpenseDTO> listOfExpenses = expenseService.getAllExpenses(page);
        return listOfExpenses.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @GetMapping("/expenses/{expenseId}")
    public ExpenseResponse getExpenseById(@PathVariable("expenseId") String expenseId) {
        ExpenseDTO expenseDTO = expenseService.getExpenseById(expenseId);
        return mapToResponse(expenseDTO);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses")
    public void deleteExpenseById(@RequestParam("expenseId") String expenseId) {
        expenseService.deleteExpenseById(expenseId);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/expenses")
    public ExpenseResponse saveExpenseDetails(@Valid @RequestBody ExpenseRequest expenseRequest) {
        ExpenseDTO expenseDTO = mapToDTO(expenseRequest);
        expenseDTO = expenseService.saveExpenseDetails(expenseDTO);
        return mapToResponse(expenseDTO);
    }

    private ExpenseDTO mapToDTO(@Valid ExpenseRequest expenseRequest) {
        return ExpenseDTO.builder()
                .name(expenseRequest.getName())
                .description(expenseRequest.getDescription())
                .amount(expenseRequest.getAmount())
                .date(expenseRequest.getDate())
                .categoryId(expenseRequest.getCategoryId())
                .build();
    }

    private ExpenseResponse mapToResponse(ExpenseDTO expenseDTO) {
        return ExpenseResponse.builder()
                .expenseId(expenseDTO.getExpenseId())
                .name(expenseDTO.getName())
                .description(expenseDTO.getDescription())
                .amount(expenseDTO.getAmount())
                .date(expenseDTO.getDate())
                .category(mapToCategoryResponse(expenseDTO.getCategoryDTO()))
                .createdAt(expenseDTO.getCreatedAt())
                .updatedAt(expenseDTO.getUpdatedAt())
                .build();
    }

    private CategoryResponse mapToCategoryResponse(CategoryDTO categoryDTO) {
        return CategoryResponse.builder()
                .categoryId(categoryDTO.getCategoryId())
                .name(categoryDTO.getName())
                .build();
    }

    @PutMapping("/expenses/{expenseId}")
    public ExpenseResponse updateExpenseDetails(@PathVariable String expenseId,@RequestBody ExpenseRequest expenseRequest){
        ExpenseDTO expenseDTO = mapToDTO(expenseRequest);
        expenseDTO = expenseService.updateExpenseDetails(expenseId, expenseDTO);
        return mapToResponse(expenseDTO);
    }

    @GetMapping("/expenses/category")
    public List<ExpenseResponse> getExpensesByCategory(@RequestParam String category, Pageable page) {
        List<ExpenseDTO> expenseDTOList = expenseService.readByCategory(category, page);
        return expenseDTOList.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @GetMapping("/expenses/name")
    public List<ExpenseResponse> getExpensesByName(@RequestParam String keyword, Pageable page) {
        List<ExpenseDTO> expenseDTOList = expenseService.readByName(keyword, page);
        return expenseDTOList.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @GetMapping("/expenses/date")
    public List<ExpenseResponse> getExpensesByDates(@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, Pageable page) {
        List<ExpenseDTO> expenseDTOList = expenseService.readByDate(startDate, endDate, page);
        return expenseDTOList.stream().map(this::mapToResponse).collect(Collectors.toList());
    }
}