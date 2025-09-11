package com.application.pennypal.interfaces.rest.controllers;

import com.application.pennypal.application.dto.input.goal.AddContributionInputModel;
import com.application.pennypal.application.dto.input.goal.AddGoalInputModel;
import com.application.pennypal.application.dto.input.goal.GoalWithdrawRequestInputModel;
import com.application.pennypal.application.dto.input.transaction.TransferInputModel;
import com.application.pennypal.application.dto.output.category.CategoryUserOutput;
import com.application.pennypal.application.dto.output.expense.TotalExpenseSummaryOutput;
import com.application.pennypal.application.dto.output.goal.GoalContributionOutput;
import com.application.pennypal.application.dto.output.goal.GoalResponseOutput;
import com.application.pennypal.application.dto.output.goal.GoalSummaryOutputModel;
import com.application.pennypal.application.dto.output.income.*;
import com.application.pennypal.application.dto.output.transaction.*;
import com.application.pennypal.application.dto.output.user.DashboardOutputModel;
import com.application.pennypal.application.dto.output.user.TransferTransaction;
import com.application.pennypal.application.dto.output.user.UserUpdateApplicationOutput;
import com.application.pennypal.application.port.in.expense.*;
import com.application.pennypal.application.port.in.goal.*;
import com.application.pennypal.application.port.in.transaction.*;
import com.application.pennypal.application.port.in.user.GetDashboardSummary;
import com.application.pennypal.application.port.in.user.GetUser;
import com.application.pennypal.application.port.in.user.UpdateUser;
import com.application.pennypal.domain.goal.entity.GoalContribution;
import com.application.pennypal.domain.valueObject.*;
import com.application.pennypal.infrastructure.adapter.out.Transaction.TransferInfrastructureService;
import com.application.pennypal.infrastructure.adapter.out.goal.GoalContributionInfraService;
import com.application.pennypal.interfaces.rest.dtos.*;
import com.application.pennypal.interfaces.rest.dtos.goal.*;
import com.application.pennypal.interfaces.rest.dtos.transaction.*;
import com.application.pennypal.interfaces.rest.dtos.user.DashboardSummaryResponse;
import com.application.pennypal.interfaces.rest.dtos.user.UpdateUserRequest;
import com.application.pennypal.interfaces.rest.mappers.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/private/user")
@RequiredArgsConstructor
public class UserController {

    private final UpdateUser updateUser;
    private final GetUser getUser;
    private final GetUserCategories getUserCategories;
    private final GetTransactionSummary getTransactionSummary;
    private final CollectPendingTransaction collectPendingTransaction;
    private final CreateTransaction createTransaction;
    private final CreateRecurringTransaction createRecurringTransaction;
    private final GetAllPendingTransactionSummary getAllPendingTransactionSummary;
    private final GetRecentTransactions getRecentTransactions;
    private final GetRecurringTransactions getRecurringTransactions;
    private final ToggleRecurringTransaction toggleRecurringTransaction;
    private final DeleteRecurringTransaction deleteRecurringTransaction;
    private final EditRecurringTransaction editRecurringTransaction;
    private final AddGoal addGoal;
    private final GetAllGoals getAllGoals;
    private final EditGoal editGoal;
    private final DeleteGoal deleteGoal;
    private final GetGoalSummary getGoalSummary;
    private final TransferInfrastructureService transferInfrastructureService;
    private final GoalContributionInfraService goalContributionInfraService;
    private final GetDashboardSummary getDashboardSummary;
    private final DashboardIncomeExpenseChart dashboardIncomeExpenseChart;
    private final GetExpenseChart getExpenseChart;
    private final GoalWithdrawRequest goalWithdrawRequest;

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

    /// TransactionManagement
    @PostMapping("/add-transaction")
    public ResponseEntity<ApiResponse<TransactionResponseDTO>> addTransactions(@Valid @RequestBody AddTransactionDTO addTransactionDTO, HttpServletRequest servletRequest){
        String token =  extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        TransactionOutputModel transactionOutputModel = createTransaction.execute(user.userId(), TransactionDtoMapper.toInput(addTransactionDTO));
        return ResponseEntity.ok(new ApiResponse<>(true,TransactionDtoMapper.toResponse(transactionOutputModel),"Transaction completed successfully"));
    }

    @GetMapping("/recent-transactions")
    public ResponseEntity<ApiResponse<List<TransactionResponseDTO>>> getRecentIncomeTransactions(@RequestParam String transactionType,@RequestParam int size, HttpServletRequest request){
        String token = extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        List<TransactionOutputModel> outputModels = getRecentTransactions.execute(user.userId(), size,TransactionType.valueOf(transactionType.toUpperCase()));
        List<TransactionResponseDTO> responseDTOS = outputModels.stream()
                .map(TransactionDtoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(new ApiResponse<>(true,responseDTOS,"Recent incomes successfully fetched"));
    }


    @PostMapping("/add-recurring-transaction")
    public ResponseEntity<ApiResponse<RecurringTransactionResponseDTO>> addRecurringTransactions(@Valid @RequestBody AddRecurringTransactionDTO transactionDTO, HttpServletRequest servletRequest){
        String token =  extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        RecurringTransactionOutputModel transactionOutputModel = createRecurringTransaction.execute(user.userId(), RecurringTransactionDtoMapper.toInput(transactionDTO));
        return ResponseEntity.ok(new ApiResponse<>(true,RecurringTransactionDtoMapper.toResponse(transactionOutputModel),"Transaction completed successfully"));
    }

    @GetMapping("/transaction/summary")
    public ResponseEntity<ApiResponse<TransactionSummaryResponseDTO>> getIncomeSummary(@RequestParam("transactionType") String transactionType,HttpServletRequest request){
        String token =  extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        TransactionSummaryOutput SummaryOutput = getTransactionSummary.execute(user.userId(),TransactionType.valueOf(transactionType.toUpperCase()));
        TransactionSummaryResponseDTO summaryResponseDTO = TransactionDtoMapper.toResponse(SummaryOutput);
        return ResponseEntity.ok(new ApiResponse<>(true,summaryResponseDTO ,"Income summary data fetched successfully"));
    }

    /// Income-management
//    @PostMapping("/add-income")
//    public ResponseEntity<ApiResponse<IncomeResponseDTO>> addIncome(@Valid @RequestBody AddIncomeRequestDTO addIncomeRequest, HttpServletRequest request){
//        String token =  extractTokenFromCookie(request,"accessToken");
//        UserDomainDTO user = getUser.get(token);
//        IncomeInputModel incomeInputModel = IncomeDtoMapper.toInput(addIncomeRequest);
//        IncomeOutputModel income = addIncome.add(incomeInputModel, user.id());
//        IncomeResponseDTO incomeResponseDTO = IncomeDtoMapper.toResponse(income);
//        return ResponseEntity.ok(new ApiResponse<>(true,incomeResponseDTO,"Income added successfully"));
//    }


//    @GetMapping("/fetch-incomes")
//    public ResponseEntity<ApiResponse<List<Income>>> getAllIncomes(HttpServletRequest request){
//        String token = extractTokenFromCookie(request,"accessToken");
//        UserDomainDTO user = getUser.get(token);
//        List<Income> incomeList = getAllIncomes.get(user.id());
//        return ResponseEntity.ok(new ApiResponse<>(true,incomeList,"Fetch all incomes successfully"));
//    }

    @GetMapping("/recurring-transactions-pending")
    public ResponseEntity<ApiResponse<AllPendingTransactionSummaryDTO>> getAllPendingIncomeSummary(@RequestParam("transactionType") String transactionType,HttpServletRequest request){
        String token = extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        AllPendingTransactionSummaryOutput allPendingSummaryOutput = getAllPendingTransactionSummary.execute(user.userId(),TransactionType.valueOf(transactionType.toUpperCase()));
        AllPendingTransactionSummaryDTO allPendingSummaryDTO = RecurringTransactionDtoMapper.toResponse(allPendingSummaryOutput);
        return ResponseEntity.ok(new ApiResponse<>(true,allPendingSummaryDTO,"Pending incomes fetched"));
    }

    @PatchMapping("/collect-pending-transaction")
    public ResponseEntity<ApiResponse<?>> collectPendingIncomes(@RequestParam("logId") String logId,HttpServletRequest request){
        String token = extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        collectPendingTransaction.collect(user.userId(),logId);
        return ResponseEntity.ok(new ApiResponse<>(true,null,"Collected the income successfully"));
    }

    @GetMapping("/recurring-transactions")
    public ResponseEntity<ApiResponse<List<RecurringTransactionResponseDTO> >> getRecurringTransactions(@RequestParam("transactionType") String transactionType,HttpServletRequest request){
        String token = extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        List<RecurringTransactionOutputModel> outputModels = getRecurringTransactions.execute(user.userId(),TransactionType.valueOf(transactionType.toUpperCase()));
        List<RecurringTransactionResponseDTO> responseDTOS = outputModels.stream()
                .map(RecurringTransactionDtoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(new ApiResponse<>(true, responseDTOS,"Fetched recurring incomes Data"));
    }

    @GetMapping("recurring-transaction-summary")
    public ResponseEntity<ApiResponse<RecurringTransactionSummaryDTO>> getPendingTransactionSummary(@RequestParam("transactionType") String transactionType,HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        List<RecurringTransactionOutputModel> outputModels = getRecurringTransactions.execute(user.userId(),TransactionType.valueOf(transactionType.toUpperCase()));
        List<RecurringTransactionResponseDTO> responseDTOS = outputModels.stream()
                .map(RecurringTransactionDtoMapper::toResponse)
                .toList();
        int count = responseDTOS.stream().filter(RecurringTransactionResponseDTO::active).toList().size();
        BigDecimal totalAmount = responseDTOS.stream().filter(RecurringTransactionResponseDTO::active).map(RecurringTransactionResponseDTO::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        RecurringTransactionSummaryDTO summaryDTO= new RecurringTransactionSummaryDTO(
                responseDTOS,count,totalAmount
        );

        return ResponseEntity.ok(new ApiResponse<>(true,summaryDTO,"Recurring transaction fetched successfully"));
    }


    @PatchMapping("/toggle-recurring-transaction-status")
    public ResponseEntity<ApiResponse<?>> deactivateRecurringIncome(@RequestParam("recurringId") String recurringId,HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
            toggleRecurringTransaction.execute(user.userId(),recurringId );
            return ResponseEntity.ok(new ApiResponse<>(true,null,"toggled transaction successfully"));
    }

    @DeleteMapping("/delete-recurring-transaction")
    public ResponseEntity<ApiResponse<?>> deleteRecurringIncome(@RequestParam("recurringId") String recurringId,HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        deleteRecurringTransaction.execute(user.userId(),recurringId);
        return ResponseEntity.ok(new ApiResponse<>(true,null,"Deleted successfully"));
    }

    @PutMapping("/edit-recurring-transaction")
    public ResponseEntity<ApiResponse<RecurringTransactionResponseDTO>> editRecurringIncome(@RequestParam("recurringId") String recurringId,@Valid @RequestBody EditRecurringTransactionDTO transactionDTO,HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        RecurringTransactionOutputModel outputModel = editRecurringTransaction.execute(user.userId(),recurringId,RecurringTransactionDtoMapper.toInput(transactionDTO));
        return  ResponseEntity.ok(new ApiResponse<>(true,RecurringTransactionDtoMapper.toResponse(outputModel),"Recurring transaction successfully edited"));

    }



    /// Expense-management
//    @PostMapping("/add-expense")
//    public ResponseEntity<ApiResponse<Expense>> addExpense(@Valid @RequestBody ExpenseRequest expenseRequest, HttpServletRequest request){
//        String token = extractTokenFromCookie(request,"accessToken");
//        UserDomainDTO user = getUser.get(token);
//        Expense expense = addExpense.add(new ExpenseDTO(expenseRequest.name(),expenseRequest.amount(),
//                expenseRequest.categoryId(),expenseRequest.type(),LocalDate.parse(expenseRequest.startDate()),
//                LocalDate.parse(expenseRequest.endDate())),user.id());
//        return ResponseEntity.ok(new ApiResponse<>(true,expense,"Add expense successfully"));
//    }
//
//    @GetMapping("/fetch-expenses")
//    public ResponseEntity<ApiResponse<List<ExpenseResponseDTO>>> getAllExpense(HttpServletRequest servletRequest){
//        String token = extractTokenFromCookie(servletRequest,"accessToken");
//        UserDomainDTO user = getUser.get(token);
//        List<ExpenseOutputModel> expenses = getAllExpenses.getAll(user.id());
//        List<ExpenseResponseDTO> expenseResponseDTOS = expenses.stream()
//                .map(ExpenseDtoMapper::toDTO)
//                .toList();
//        return ResponseEntity.ok(new ApiResponse<>(true,expenseResponseDTOS,"All expenses fetched successfully"));
//    }
//
//    @PutMapping("/expense/edit-expense")
//    public ResponseEntity<ApiResponse<String>> editExpense(@Valid @RequestBody EditExpenseRequestDTO expenseRequestDTO, HttpServletRequest servletRequest){
//        String token = extractTokenFromCookie(servletRequest,"accessToken");
//        UserDomainDTO user = getUser.get(token);
//        ExpenseInputModel expenseInputModel = new ExpenseInputModel(
//                expenseRequestDTO.id(),
//                expenseRequestDTO.name(),
//                expenseRequestDTO.categoryId(),
//                expenseRequestDTO.amount(),
//                LocalDate.parse(expenseRequestDTO.startDate()),
//                LocalDate.parse(expenseRequestDTO.endDate()),
//                RecurrenceFrequency.valueOf(expenseRequestDTO.type().toUpperCase())
//        );
//        editExpense.execute(user.id(), expenseInputModel);
//        return ResponseEntity.ok(new ApiResponse<>(true,"Edit completed successfully","Completed"));
//    }
//
//    @DeleteMapping("/expense/delete-expense")
//    public ResponseEntity<ApiResponse<String>> deleteExpense(@RequestParam Long id,HttpServletRequest servletRequest){
//        String token = extractTokenFromCookie(servletRequest,"accessToken");
//        UserDomainDTO user = getUser.get(token);
//        deleteExpense.execute(user.id(), id);
//        return ResponseEntity.ok(new ApiResponse<>(true,"Deletion completed successfully","Completed"));
//    }

    /// Goal Management
    @PostMapping("/goal/add-goal")
    public ResponseEntity<ApiResponse<GoalResponseDTO>> addNewGoal(@Valid @RequestBody AddGoalRequestDTO addGoalRequestDTO, HttpServletRequest servletRequest)
    {
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        AddGoalInputModel addGoalInputModel = GoalDtoMapper.toInput(addGoalRequestDTO);
        GoalResponseOutput goalResponseOutput = addGoal.execute(addGoalInputModel,user.userId());
        GoalResponseDTO goalResponseDTO = GoalDtoMapper.toDto(goalResponseOutput);
        return ResponseEntity.ok(new ApiResponse<>(true,goalResponseDTO,"New goal created successfully"));
    }
    @GetMapping("/goal/get-all-goals")
    public ResponseEntity<ApiResponse<List<GoalResponseDTO>>> getAllGoals(HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        List<GoalResponseOutput> goalResponseOutputs = getAllGoals.execute(user.userId());
        List<GoalResponseDTO> goalResponseDTOS = goalResponseOutputs.stream()
                .map(GoalDtoMapper::toDto)
                .toList();
        return ResponseEntity.ok(new ApiResponse<>(true,goalResponseDTOS,"All goal fetched successfully"));
    }
    @PostMapping("/goal/add-contribution")
    public ResponseEntity<ApiResponse<GoalContributionResponseDTO>> addContribution(@Valid @RequestBody AddContributionRequestDTO addContributionRequestDTO, HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        GoalContributionOutput output = goalContributionInfraService.addContribution(user.userId(), new AddContributionInputModel(
                user.userId(),
                addContributionRequestDTO.goalId(),
                addContributionRequestDTO.cardId(),
                addContributionRequestDTO.amount(),
                addContributionRequestDTO.notes()

        ));
        GoalContributionResponseDTO responseDTO = new GoalContributionResponseDTO(
                output.contributionId(),
                output.amount(),
                output.date(),
                output.notes(),
                output.coins()
        );
        return ResponseEntity.ok(new ApiResponse<>(true,responseDTO,"Success"));
    }
    @PutMapping("/goal/edit-goal")
    public ResponseEntity<ApiResponse<String>> editGoal(@Valid @RequestBody EditGoalRequestDTO editGoalInputModel, HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        editGoal.execute(user.userId(), GoalDtoMapper.toInput(editGoalInputModel));
        return ResponseEntity.ok(new ApiResponse<>(true,"Edit successfully completed","SUCCESS"));
    }

    @DeleteMapping("/goal/delete-goal")
    public ResponseEntity<ApiResponse<String>> deleteGoal(@RequestParam("goalId") String goalId,HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        deleteGoal.execute(user.userId(), goalId);
        return ResponseEntity.ok(new ApiResponse<>(true,"Deleted successfully","Success"));
    }
    @GetMapping("/goal/summary")
    public ResponseEntity<ApiResponse<GoalSummaryResponseDTO>> getGoalSummary(HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        GoalSummaryOutputModel goalSummaryOutputModel = getGoalSummary.execute(user.userId());
        GoalSummaryResponseDTO goalSummaryResponseDTO = new GoalSummaryResponseDTO(
                goalSummaryOutputModel.totalActiveGoals(),
                goalSummaryOutputModel.totalSaved(),
                goalSummaryOutputModel.completedGoals()
                );
        return ResponseEntity.ok(new ApiResponse<>(true,goalSummaryResponseDTO,"The goal summary has fetched successfully"));
    }

    @PostMapping("/goal/withdraw-money")
    public ResponseEntity<ApiResponse<?>> withdrawRequest(@RequestParam String goalId, HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        goalWithdrawRequest.execute(new GoalWithdrawRequestInputModel(
                user.userId(),
                goalId
        ));
        return ResponseEntity.ok(new ApiResponse<>(true,null,"Withdraw Request completed successfully"));
    }


//
//

    /// Categories management
    @GetMapping("/get-categories")
    public ResponseEntity<ApiResponse<List<CategoryUserOutput>>> getUserCategories(){
        List<CategoryUserOutput> categoryUserResponseDTOS = getUserCategories.get();
        return ResponseEntity.ok(new ApiResponse<>(true,categoryUserResponseDTOS,"Fetched user categories successfully"));
    }


    /// Transfer management

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<TransferTransaction>> transferMoney(@Valid @RequestBody TransferRequestDTO requestDTO,HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        TransferTransaction transaction = transferInfrastructureService.transfer(
                new TransferInputModel(
                        user.userId(),
                        requestDTO.recipientId(),
                        requestDTO.paymentMethodId(),
                        "card",
                        requestDTO.amount(),
                        requestDTO.note(),
                        requestDTO.pin()
                )
        );

        return ResponseEntity.ok(new ApiResponse<>(true,transaction,"fetched successfully"));
    }

    @GetMapping("/dashboard-summary")
    public ResponseEntity<ApiResponse<DashboardSummaryResponse>> getDashboardSummary(HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        DashboardOutputModel outputModel = getDashboardSummary.execute(user.userId());
        return ResponseEntity.ok(new ApiResponse<>(true,new DashboardSummaryResponse(
                outputModel.expenseOutput(),
                outputModel.incomeOutput()
        ),"fetched successfully"));
    }

    @GetMapping("/income-expense-chart")
    public ResponseEntity<ApiResponse<List<DashIncExpChart>>> getIncomeExpenseChart(HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        List<DashIncExpChart> chartList = dashboardIncomeExpenseChart.execute(user.userId());
        return ResponseEntity.ok(new ApiResponse<>(true,chartList,"fetched successfully"));
    }

    @GetMapping("/expense-breakdown-chart")
    public ResponseEntity<ApiResponse<List<ExpenseDataChart>>> getExpenseChart(HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        List<ExpenseDataChart> expenseDataCharts = getExpenseChart.execute(user.userId());
        return ResponseEntity.ok(new ApiResponse<>(true,expenseDataCharts,"fetched successfully"));
    }

    /// helper methods
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
