package com.application.pennypal.interfaces.rest.controllers;

import com.application.pennypal.application.input.expense.ExpenseInputModel;
import com.application.pennypal.application.input.goal.AddGoalInputModel;
import com.application.pennypal.application.input.goal.EditGoalInputModel;
import com.application.pennypal.application.input.income.IncomeInputModel;
import com.application.pennypal.application.output.category.CategoryUserOutput;
import com.application.pennypal.application.output.expense.ExpenseOutputModel;
import com.application.pennypal.application.output.goal.GoalResponseOutput;
import com.application.pennypal.application.output.goal.GoalSummaryOutputModel;
import com.application.pennypal.application.output.income.*;
import com.application.pennypal.application.output.user.UserUpdateApplicationOutput;
import com.application.pennypal.application.usecases.Income.*;
import com.application.pennypal.application.usecases.expense.*;
import com.application.pennypal.application.usecases.goal.*;
import com.application.pennypal.application.usecases.user.GetUser;
import com.application.pennypal.application.usecases.user.UpdateUser;
import com.application.pennypal.domain.entity.Expense;
import com.application.pennypal.domain.entity.Income;
import com.application.pennypal.domain.valueObject.*;
import com.application.pennypal.interfaces.rest.dtos.*;
import com.application.pennypal.interfaces.rest.dtos.Expense.EditExpenseRequestDTO;
import com.application.pennypal.interfaces.rest.dtos.Expense.ExpenseRequest;
import com.application.pennypal.interfaces.rest.dtos.Expense.ExpenseResponseDTO;
import com.application.pennypal.interfaces.rest.dtos.goal.*;
import com.application.pennypal.interfaces.rest.dtos.income.*;
import com.application.pennypal.interfaces.rest.dtos.user.UpdateUserRequest;
import com.application.pennypal.interfaces.rest.mappers.CategoryDtoMapper;
import com.application.pennypal.interfaces.rest.mappers.ExpenseDtoMapper;
import com.application.pennypal.interfaces.rest.mappers.GoalDtoMapper;
import com.application.pennypal.interfaces.rest.mappers.IncomeDtoMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UpdateUser updateUser;
    private final AddIncome addIncome;
    private final GetUser getUser;
    private final GetAllIncomes getAllIncomes;
    private final AddExpense addExpense;
    private final GetAllExpenses getAllExpenses;
    private final GetRecentIncomes getRecentIncomes;
    private final GetUserCategories getUserCategories;
    private final GetRecurringIncomesData getRecurringIncomesData;
    private final ToggleRecurrenceIncome toggleRecurrenceIncome;
    private final DeleteRecurringIncome deleteRecurringIncome;
    private final GetIncomeSummary getIncomeSummary;
    private final GetRecentIncomeTransactions getRecentIncomeTransactions;
    private final GetAllPendingIncomeSummary allPendingIncomeSummary;
    private final CollectPendingIncomes collectPendingIncomes;
    private final AddGoal addGoal;
    private final GetAllGoals getAllGoals;
    private final AddContribution addContribution;
    private final EditGoal editGoal;
    private final DeleteGoal deleteGoal;
    private final GetGoalSummary getGoalSummary;
    private final EditExpense editExpense;
    private final DeleteExpense deleteExpense;

    @PatchMapping("/update-user")
    public ResponseEntity<ApiResponse<UserDomainDTO>> updateUser(@ModelAttribute UpdateUserRequest user, HttpServletRequest request, HttpServletResponse response){
        if(user != null){
            String token = extractTokenFromCookie(request,"accessToken");
            UserDomainDTO updatedUser = updateUser.update(new UserUpdateApplicationOutput(
                    user.name(),
                    user.email(),
                    user.phone(),
                    user.profilePicture()
            ),token);
            if(user.email()!= null){
                ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", "")
                        .httpOnly(true)
                        .secure(true)
                        .sameSite("Strict")
                        .path("/")
                        .maxAge(0)
                        .build();

                // Clear refresh token cookie
                ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", "")
                        .httpOnly(true)
                        .secure(true)
                        .sameSite("Strict")
                        .path("/")
                        .maxAge(0)
                        .build();

                // Add cleared cookies to response
                response.addHeader("Set-Cookie", accessTokenCookie.toString());
                response.addHeader("Set-Cookie", refreshTokenCookie.toString());
            }
            return ResponseEntity.ok(new ApiResponse<>(true,updatedUser,"User updated successfully"));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse<>(false,null,"Empty arguments."));
    }

    /// Income-management
    @PostMapping("/add-income")
    public ResponseEntity<ApiResponse<IncomeResponseDTO>> addIncome(@Valid @RequestBody AddIncomeRequestDTO addIncomeRequest, HttpServletRequest request){
        String token =  extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        IncomeInputModel incomeInputModel = IncomeDtoMapper.toInput(addIncomeRequest);
        IncomeOutputModel income = addIncome.add(incomeInputModel, user.id());
        IncomeResponseDTO incomeResponseDTO = IncomeDtoMapper.toResponse(income);
        return ResponseEntity.ok(new ApiResponse<>(true,incomeResponseDTO,"Income added successfully"));
    }


    @GetMapping("/income/summary")
    public ResponseEntity<ApiResponse<IncomeSummaryResponseDTO>> getIncomeSummary(HttpServletRequest request){
        String token =  extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        IncomeSummaryOutput incomeSummaryOutput = getIncomeSummary.execute(user.id());
        IncomeSummaryResponseDTO incomeSummaryResponseDTO = IncomeDtoMapper.toResponse(incomeSummaryOutput);
        return ResponseEntity.ok(new ApiResponse<>(true, incomeSummaryResponseDTO,"Income summary data fetched successfully"));
    }

    @GetMapping("/fetch-incomes")
    public ResponseEntity<ApiResponse<List<Income>>> getAllIncomes(HttpServletRequest request){
        String token = extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        List<Income> incomeList = getAllIncomes.get(user.id());
        return ResponseEntity.ok(new ApiResponse<>(true,incomeList,"Fetch all incomes successfully"));
    }

    @GetMapping("/income/all-pending-incomes-summary")
    public ResponseEntity<ApiResponse<AllPendingIncomeSummaryDTO>> getAllPendingIncomeSummary(HttpServletRequest request){
        String token = extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        AllPendingIncomeSummaryOutput allPendingIncomeSummaryOutput = allPendingIncomeSummary.execute(user.id());
        AllPendingIncomeSummaryDTO allPendingIncomeSummaryDTO = IncomeDtoMapper.toResponse(allPendingIncomeSummaryOutput);
        return ResponseEntity.ok(new ApiResponse<>(true,allPendingIncomeSummaryDTO,"Pending incomes fetched"));
    }

    @PatchMapping("/income/collect-pending")
    public ResponseEntity<ApiResponse<?>> collectPendingIncomes(@Valid @RequestBody CollectPendingIncomeRequestDTO collectPendingIncomeRequestDTO ,HttpServletRequest request){
        String token = extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        collectPendingIncomes.collect(user.id(), collectPendingIncomeRequestDTO.incomeId(),collectPendingIncomeRequestDTO.incomeDate());
        return ResponseEntity.ok(new ApiResponse<>(true,null,"Collected the income successfully"));
    }

    @GetMapping("/income/recent-transactions")
    public ResponseEntity<ApiResponse<List<IncomeTransactionResponseDTO>>> getRecentIncomeTransactions(HttpServletRequest request, @PathParam("size") int size){
        String token = extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        List<IncomeTransactionOutputModel> transactionOutputModelList = getRecentIncomeTransactions.execute(user.id(),size);
        List<IncomeTransactionResponseDTO> transactionResponseDTOS = transactionOutputModelList.stream()
                .map(incomeTransactionOutputModel -> {
                    return new IncomeTransactionResponseDTO(
                            incomeTransactionOutputModel.id(),
                            incomeTransactionOutputModel.income().title(),
                            incomeTransactionOutputModel.amount(),
                            incomeTransactionOutputModel.transactionDate(),
                            CategoryDtoMapper.toResponse(incomeTransactionOutputModel.categoryUserOutput())
                    );
                }).toList();
        return ResponseEntity.ok(new ApiResponse<>(true,transactionResponseDTOS,"Recent incomes successfully fetched"));
    }

    @GetMapping("/income/recurring-incomes")
    public ResponseEntity<ApiResponse<RecurringIncomesDataOutput>> getRecurringIncomes(HttpServletRequest request){
        String token = extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        RecurringIncomesDataOutput recurringIncomesDataOutput = getRecurringIncomesData.execute(user.id());
        return ResponseEntity.ok(new ApiResponse<>(true, recurringIncomesDataOutput,"Fetched recurring incomes Data"));
    }

    @PatchMapping("/toggle-recurring-income/{id}")
    public ResponseEntity<ApiResponse<?>> deactivateRecurringIncome(@PathVariable("id") Long incomeId){
            toggleRecurrenceIncome.toggle(incomeId);
            return ResponseEntity.ok(new ApiResponse<>(true,null,"Deactivated income successfully"));
    }

    @DeleteMapping("/delete-recurring-income/{id}")
    public ResponseEntity<ApiResponse<?>> deleteRecurringIncome(@PathVariable("id") Long incomeId){
        deleteRecurringIncome.deleteById(incomeId);
        return ResponseEntity.ok(new ApiResponse<>(true,null,"Deleted successfully"));
    }



    /// Expense-management
    @PostMapping("/add-expense")
    public ResponseEntity<ApiResponse<Expense>> addExpense(@Valid @RequestBody ExpenseRequest expenseRequest, HttpServletRequest request){
        String token = extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        Expense expense = addExpense.add(new ExpenseDTO(expenseRequest.name(),expenseRequest.amount(),
                expenseRequest.categoryId(),expenseRequest.type(),LocalDate.parse(expenseRequest.startDate()),
                LocalDate.parse(expenseRequest.endDate())),user.id());
        return ResponseEntity.ok(new ApiResponse<>(true,expense,"Add expense successfully"));
    }

    @GetMapping("/fetch-expenses")
    public ResponseEntity<ApiResponse<List<ExpenseResponseDTO>>> getAllExpense(HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        List<ExpenseOutputModel> expenses = getAllExpenses.getAll(user.id());
        List<ExpenseResponseDTO> expenseResponseDTOS = expenses.stream()
                .map(ExpenseDtoMapper::toDTO)
                .toList();
        return ResponseEntity.ok(new ApiResponse<>(true,expenseResponseDTOS,"All expenses fetched successfully"));
    }

    @PutMapping("/expense/edit-expense")
    public ResponseEntity<ApiResponse<String>> editExpense(@Valid @RequestBody EditExpenseRequestDTO expenseRequestDTO, HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        ExpenseInputModel expenseInputModel = new ExpenseInputModel(
                expenseRequestDTO.id(),
                expenseRequestDTO.name(),
                expenseRequestDTO.categoryId(),
                expenseRequestDTO.amount(),
                LocalDate.parse(expenseRequestDTO.startDate()),
                LocalDate.parse(expenseRequestDTO.endDate()),
                RecurrenceFrequency.valueOf(expenseRequestDTO.type().toUpperCase())
        );
        editExpense.execute(user.id(), expenseInputModel);
        return ResponseEntity.ok(new ApiResponse<>(true,"Edit completed successfully","Completed"));
    }

    @DeleteMapping("/expense/delete-expense")
    public ResponseEntity<ApiResponse<String>> deleteExpense(@RequestParam Long id,HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        deleteExpense.execute(user.id(), id);
        return ResponseEntity.ok(new ApiResponse<>(true,"Deletion completed successfully","Completed"));
    }

    /// Goal Management
    @PostMapping("/goal/add-goal")
    public ResponseEntity<ApiResponse<GoalResponseDTO>> addNewGoal(@Valid @RequestBody AddGoalRequestDTO addGoalRequestDTO, HttpServletRequest servletRequest)
    {
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        AddGoalInputModel addGoalInputModel = GoalDtoMapper.toInput(addGoalRequestDTO);
        GoalResponseOutput goalResponseOutput = addGoal.execute(addGoalInputModel,user.id());
        GoalResponseDTO goalResponseDTO = GoalDtoMapper.toDto(goalResponseOutput);
        return ResponseEntity.ok(new ApiResponse<>(true,goalResponseDTO,"New goal created successfully"));
    }

    @GetMapping("/goal/get-all-goals")
    public ResponseEntity<ApiResponse<List<GoalResponseDTO>>> getAllGoals(HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        List<GoalResponseOutput> goalResponseOutputs = getAllGoals.execute(user.id());
        List<GoalResponseDTO> goalResponseDTOS = goalResponseOutputs.stream()
                .map(GoalDtoMapper::toDto)
                .toList();
        return ResponseEntity.ok(new ApiResponse<>(true,goalResponseDTOS,"All goal fetched successfully"));
    }

    @PostMapping("/goal/add-contribution")
    public ResponseEntity<ApiResponse<String>> addContribution(@Valid @RequestBody AddContributionRequestDTO addContributionRequestDTO, HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        addContribution.execute(user.id(), addContributionRequestDTO.goalId(),addContributionRequestDTO.amount(),addContributionRequestDTO.notes());
        return ResponseEntity.ok(new ApiResponse<>(true,"Add contribution successfully","Success"));
    }

    @PutMapping("/goal/edit-goal")
    public ResponseEntity<ApiResponse<String>> editGoal(@Valid @RequestBody EditGoalRequestDTO editGoalInputModel, HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        editGoal.execute(user.id(), GoalDtoMapper.toInput(editGoalInputModel));
        return ResponseEntity.ok(new ApiResponse<>(true,"Edit successfully completed","SUCCESS"));
    }

    @DeleteMapping("/goal/delete-goal")
    public ResponseEntity<ApiResponse<String>> deleteGoal(@RequestParam("id") Long goalId,HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        deleteGoal.execute(user.id(), goalId);
        return ResponseEntity.ok(new ApiResponse<>(true,"Deleted successfully","Success"));
    }

    @GetMapping("/goal/summary")
    public ResponseEntity<ApiResponse<GoalSummaryResponseDTO>> getGoalSummary(HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        GoalSummaryOutputModel goalSummaryOutputModel = getGoalSummary.execute(user.id());
        GoalSummaryResponseDTO goalSummaryResponseDTO = new GoalSummaryResponseDTO(
                goalSummaryOutputModel.totalActiveGoals(),
                goalSummaryOutputModel.totalSaved(),
                goalSummaryOutputModel.completedGoals()
                );
        return ResponseEntity.ok(new ApiResponse<>(true,goalSummaryResponseDTO,"The goal summary has fetched successfully"));
    }



    /// Categories management
    @GetMapping("/get-categories")
    public ResponseEntity<ApiResponse<List<CategoryUserOutput>>> getUserCategories(){
        List<CategoryUserOutput> categoryUserResponseDTOS = getUserCategories.get();
        return ResponseEntity.ok(new ApiResponse<>(true,categoryUserResponseDTOS,"Fetched user categories successfully"));
    }


    private String extractTokenFromCookie(HttpServletRequest request, String tokenName) {
        if(request.getCookies() != null){
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> tokenName.equals(cookie.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }
        return null;
    }



}
