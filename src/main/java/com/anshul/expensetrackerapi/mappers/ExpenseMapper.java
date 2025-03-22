package com.anshul.expensetrackerapi.mappers;

import com.anshul.expensetrackerapi.Entity.Expense;
import com.anshul.expensetrackerapi.dto.ExpenseDTO;
import com.anshul.expensetrackerapi.io.ExpenseRequest;
import com.anshul.expensetrackerapi.io.ExpenseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    @Mapping(target = "category", source = "expenseDTO.categoryDTO")
    ExpenseResponse mapToExpenseResponse(ExpenseDTO expenseDTO);

    ExpenseDTO mapToExpenseDTO(ExpenseRequest expenseRequest);

    Expense mapToExpense(ExpenseDTO expenseDTO);

    @Mapping(target = "categoryDTO", source = "expense.category")
    ExpenseDTO mapToExpenseDTO(Expense expense);
}
