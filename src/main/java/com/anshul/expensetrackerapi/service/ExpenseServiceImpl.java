package com.anshul.expensetrackerapi.service;

import com.anshul.expensetrackerapi.Entity.Category;
import com.anshul.expensetrackerapi.Entity.Expense;
import com.anshul.expensetrackerapi.dto.ExpenseDTO;
import com.anshul.expensetrackerapi.exception.ResourceNotFoundException;
import com.anshul.expensetrackerapi.mappers.ExpenseMapper;
import com.anshul.expensetrackerapi.repository.CategoryRepository;
import com.anshul.expensetrackerapi.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    @Autowired
    private UserService userService;

    @Autowired
    private ExpenseRepository expenseRepo;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExpenseMapper expenseMapper;

    @Override
    public List<ExpenseDTO> getAllExpenses(Pageable page) {
        List<Expense> listOfExpense = expenseRepo.findByUserId(userService.getLoggedInUser().getId(), page).toList();
        return listOfExpense.stream().map(expense -> expenseMapper.mapToExpenseDTO(expense)).collect(Collectors.toList());
    }

    @Override
    public ExpenseDTO getExpenseById(String expenseId) {
        Expense exsistingExpense = getExpenseEntity(expenseId);
        return expenseMapper.mapToExpenseDTO(exsistingExpense);
    }

    private Expense getExpenseEntity(String expenseId) {
        Optional<Expense> expense = expenseRepo.findByUserIdAndExpenseId(userService.getLoggedInUser().getId(), expenseId);

        if(!expense.isPresent()){
            throw new ResourceNotFoundException("Expense is not found for the Id" + expenseId);
        }
        return expense.get();
    }

    @Override
    public void deleteExpenseById(String expenseId){
        Expense expense = getExpenseEntity(expenseId);
        expenseRepo.delete(expense);
    }

    @Override
    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO){
        Optional<Category> optionalCategory = categoryRepository.findByUserIdAndCategoryId(userService.getLoggedInUser().getId(), expenseDTO.getCategoryId());

        if(!optionalCategory.isPresent()){
            throw new ResourceNotFoundException("Category is not found for the Id" + expenseDTO.getCategoryId());
        }
        expenseDTO.setExpenseId(UUID.randomUUID().toString());
        Expense expense = expenseMapper.mapToExpense(expenseDTO);
        expense.setCategory(optionalCategory.get());
        expense.setUser(userService.getLoggedInUser());
        expense = expenseRepo.save(expense);
        return expenseMapper.mapToExpenseDTO(expense);
    }

    @Override
    public ExpenseDTO updateExpenseDetails(String expenseId, ExpenseDTO expenseDTO){

        Expense oldExpense = getExpenseEntity(expenseId);
        if(expenseDTO.getCategoryId() != null){
            Optional<Category> optionalCategory = categoryRepository.findByUserIdAndCategoryId(userService.getLoggedInUser().getId(), expenseDTO.getCategoryId());
            if(!optionalCategory.isPresent()){
                throw new ResourceNotFoundException("Category is not found for the Id" + expenseDTO.getCategoryId());
            }
            oldExpense.setCategory(optionalCategory.get());
        }
        oldExpense.setName(expenseDTO.getName() != null ? expenseDTO.getName() : oldExpense.getName());
        oldExpense.setDescription(expenseDTO.getDescription() != null ? expenseDTO.getDescription() : oldExpense.getDescription());
        oldExpense.setAmount(expenseDTO.getAmount() != null ? expenseDTO.getAmount() : oldExpense.getAmount());
        oldExpense.setDate(expenseDTO.getDate() != null ? expenseDTO.getDate() : oldExpense.getDate());

        Expense newExpense = expenseRepo.save(oldExpense);
        return expenseMapper.mapToExpenseDTO(newExpense);
    }

    @Override
    public List<ExpenseDTO> readByCategory(String category, Pageable page) {
        Optional<Category> optionalCategory = categoryRepository.findByNameAndUserId(category, userService.getLoggedInUser().getId());

        if(!optionalCategory.isPresent()){
            throw new ResourceNotFoundException("Category is not found for the name" + category);
        }
        List<Expense> list = expenseRepo.findByUserIdAndCategoryId(userService.getLoggedInUser().getId(), optionalCategory.get().getId(), page).toList();
        return list.stream().map(expense -> expenseMapper.mapToExpenseDTO(expense)).collect(Collectors.toList());
    }

    @Override
    public List<ExpenseDTO> readByName(String keyword, Pageable page) {
        List<Expense> list = expenseRepo.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(),keyword, page).toList();
        return list.stream().map(expense -> expenseMapper.mapToExpenseDTO(expense)).collect(Collectors.toList());
    }

    @Override
    public List<ExpenseDTO> readByDate(Date startDate, Date endDaTe, Pageable page) {

        if(startDate == null){
            startDate = new Date(0);
        }
        if(endDaTe == null){
            endDaTe = new Date(System.currentTimeMillis());
        }
        List<Expense> listOfExpense = expenseRepo.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(), startDate, endDaTe, page);

        return listOfExpense.stream().map(expense -> expenseMapper.mapToExpenseDTO(expense)).collect(Collectors.toList());

    }
}
