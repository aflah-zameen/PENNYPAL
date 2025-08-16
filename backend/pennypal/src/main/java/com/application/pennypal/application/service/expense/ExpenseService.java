//package com.application.pennypal.application.service.expense;
//
//import com.application.pennypal.application.exception.base.ApplicationBusinessException;
//import com.application.pennypal.application.dto.input.expense.ExpenseInputModel;
//import com.application.pennypal.application.mappers.expense.ExpenseApplicationMapper;
//import com.application.pennypal.application.dto.output.expense.ExpenseOutputModel;
//import com.application.pennypal.application.port.out.repository.ExpenseRepositoryPort;
//import com.application.pennypal.application.port.in.expense.AddExpense;
//import com.application.pennypal.application.port.in.expense.DeleteExpense;
//import com.application.pennypal.application.port.in.expense.EditExpense;
//import com.application.pennypal.application.port.in.expense.GetAllExpenses;
//import com.application.pennypal.domain.entity.Expense;
//import com.application.pennypal.domain.valueObject.ExpenseDTO;
//import lombok.RequiredArgsConstructor;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//public class ExpenseService implements AddExpense, GetAllExpenses, EditExpense, DeleteExpense {
//    private final ExpenseRepositoryPort expenseRepositoryPort;
//    private final ExpenseApplicationMapper expenseApplicationMapper;
//    @Override
//    public Expense add(ExpenseDTO expense,Long userId) {
//        return expenseRepositoryPort.save(expense,userId);
//    }
//
//    @Override
//    public List<ExpenseOutputModel> getAll(Long userId) {
//        List<Expense> expenses = expenseRepositoryPort.fetchAllExpenses(userId);
//        return expenses.stream()
//                .map(expenseApplicationMapper::toOutput)
//                .toList();
//
//    }
//
//    @Override
//    public void execute(Long userId, ExpenseInputModel expenseInputModel) {
//        Expense expense = expenseRepositoryPort.getExpenseById(expenseInputModel.id())
//                .orElseThrow(() -> new ApplicationBusinessException("Expense cannot be found","NOT_FOUND"));
//        if(expense.getUserId().equals(userId)){
//            expense.setName(expenseInputModel.name());
//            expense.setAmount(expenseInputModel.amount());
//            expense.setType(expenseInputModel.type().getValue());
//            expense.setStartDate(expenseInputModel.startDate());
//            expense.setEndDate(expenseInputModel.endDate());
//            expense.setCategoryId(expenseInputModel.categoryId());
//            expenseRepositoryPort.save(expense);
//        }else{
//            throw new ApplicationBusinessException("User action is unauthorized","UNAUTHORIZED_ACTION");
//        }
//    }
//
//    @Override
//    public void execute(Long userId, Long expenseId) {
//        Expense expense = expenseRepositoryPort.getExpenseById(expenseId)
//                .orElseThrow(() -> new ApplicationBusinessException("Expense cannot be found","NOT_FOUND"));
//        if(expense.getUserId().equals(userId)){
//            expense.deleteExpense();
//            expenseRepositoryPort.save(expense);
//        }else{
//            throw new ApplicationBusinessException("User action is unauthorized","UNAUTHORIZED_ACTION");
//        }
//    }
//}
