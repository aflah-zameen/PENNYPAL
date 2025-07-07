package com.application.pennypal.interfaces.rest;

import com.application.pennypal.application.dto.CategoryUserResponseDTO;
import com.application.pennypal.application.dto.RecurringIncomeDTO;
import com.application.pennypal.application.dto.RecurringIncomesDataDTO;
import com.application.pennypal.application.dto.UserUpdateApplicationDTO;
import com.application.pennypal.application.usecases.Income.*;
import com.application.pennypal.application.usecases.expense.AddExpense;
import com.application.pennypal.application.usecases.expense.GetAllExpenses;
import com.application.pennypal.application.usecases.expense.GetUserCategories;
import com.application.pennypal.application.usecases.user.GetUser;
import com.application.pennypal.application.usecases.user.UpdateUser;
import com.application.pennypal.domain.user.entity.Expense;
import com.application.pennypal.domain.user.entity.Income;
import com.application.pennypal.domain.user.valueObject.ExpenseDTO;
import com.application.pennypal.domain.user.valueObject.IncomeDTO;
import com.application.pennypal.domain.user.valueObject.RecurrenceFrequency;
import com.application.pennypal.domain.user.valueObject.UserDomainDTO;
import com.application.pennypal.interfaces.rest.dtos.ApiResponse;
import com.application.pennypal.interfaces.rest.dtos.ExpenseRequest;
import com.application.pennypal.interfaces.rest.dtos.IncomeRequest;
import com.application.pennypal.interfaces.rest.dtos.UpdateUserRequest;
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

import java.math.BigDecimal;
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
    private final GetTotalIncome getTotalIncome;
    private final GetAllIncomes getAllIncomes;
    private final AddExpense addExpense;
    private final GetAllExpenses getAllExpenses;
    private final GetRecentIncomes getRecentIncomes;
    private final GetUserCategories getUserCategories;
    private final GetRecurringIncomesData getRecurringIncomesData;
    private final ToggleRecurrenceIncome toggleRecurrenceIncome;
    private final DeleteRecurringIncome deleteRecurringIncome;

    @PatchMapping("/update-user")
    public ResponseEntity<ApiResponse<UserDomainDTO>> updateUser(@ModelAttribute UpdateUserRequest user, HttpServletRequest request, HttpServletResponse response){
        if(user != null){
            String token = extractTokenFromCookie(request,"accessToken");
            UserDomainDTO updatedUser = updateUser.update(new UserUpdateApplicationDTO(
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

    //income-management
    @PostMapping("/add-income")
    public ResponseEntity<ApiResponse<Income>> addIncome(@Valid @RequestBody IncomeRequest incomeRequest,HttpServletRequest request){
        String token =  extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        Income income = addIncome.add(new IncomeDTO(incomeRequest.amount(),incomeRequest.source(),
                LocalDate.parse(incomeRequest.income_date()),"PENDING",null,incomeRequest.notes(),incomeRequest.recurrence(), !incomeRequest.frequency().isBlank() ? RecurrenceFrequency.valueOf(incomeRequest.frequency().toUpperCase()) : null,incomeRequest.recurrence()),user.id());
        return ResponseEntity.ok(new ApiResponse<>(true,income,"Income added successfully"));
    }

    @GetMapping("/total-income")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalIncome(HttpServletRequest request){
        String token =  extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        BigDecimal totalIncome = getTotalIncome.get(user.id(),LocalDate.now());
        return ResponseEntity.ok(new ApiResponse<>(true,totalIncome,"Total income fetched successfully"));
    }

    @GetMapping("/fetch-incomes")
    public ResponseEntity<ApiResponse<List<Income>>> getAllIncomes(HttpServletRequest request){
        String token = extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        List<Income> incomeList = getAllIncomes.get(user.id());
        return ResponseEntity.ok(new ApiResponse<>(true,incomeList,"Fetch all incomes successfully"));
    }

    @GetMapping("/recent-incomes")
    public ResponseEntity<ApiResponse<List<Income>>> getRecentIncomes(HttpServletRequest request, @PathParam("size") int size){
        String token = extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        List<Income> incomeList = getRecentIncomes.get(user.id(),size );
        return ResponseEntity.ok(new ApiResponse<>(true,incomeList,"Recent incomes successfully fetched"));
    }

    @GetMapping("/get-recurring-incomes-data")
    public ResponseEntity<ApiResponse<RecurringIncomesDataDTO>> getRecurringIncomes(HttpServletRequest request){
        String token = extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        RecurringIncomesDataDTO recurringIncomesDataDTO = getRecurringIncomesData.execute(user.id());
        return ResponseEntity.ok(new ApiResponse<>(true,recurringIncomesDataDTO,"Fetched recurring incomes Data"));
    }

    @PatchMapping("/toggle-recurring-income/{id}")
    public ResponseEntity<ApiResponse<RecurringIncomeDTO>> deactivateRecurringIncome(@PathVariable("id") Long incomeId){
            RecurringIncomeDTO recurringIncomeDTO = toggleRecurrenceIncome.toggle(incomeId);
            return ResponseEntity.ok(new ApiResponse<>(true,recurringIncomeDTO,"Deactivated income successfully"));
    }

    @DeleteMapping("/delete-recurring-income/{id}")
    public ResponseEntity<ApiResponse<?>> deleteRecurringIncome(@PathVariable("id") Long incomeId){
        deleteRecurringIncome.deleteById(incomeId);
        return ResponseEntity.ok(new ApiResponse<>(true,null,"Deletd successfully"));
    }


    //expense-management
    @PostMapping("/add-expense")
    public ResponseEntity<ApiResponse<Expense>> addExpense(@Valid @RequestBody ExpenseRequest expenseRequest, HttpServletRequest request){
        String token = extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        Expense expense = addExpense.add(new ExpenseDTO(expenseRequest.name(),expenseRequest.amount(),
                expenseRequest.category(),expenseRequest.type(),LocalDate.parse(expenseRequest.startDate()),
                LocalDate.parse(expenseRequest.endDate())),user.id());
        return ResponseEntity.ok(new ApiResponse<>(true,expense,"Add expense successfully"));
    }

    @GetMapping("/fetch-expenses")
    public ResponseEntity<ApiResponse<List<Expense>>> getAllExpense(HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        List<Expense> expenses = getAllExpenses.getAll(user.id());
        return ResponseEntity.ok(new ApiResponse<>(true,expenses,"All expenses fetched successfully"));
    }

    //categories management
    @GetMapping("/get-categories")
    public ResponseEntity<ApiResponse<List<CategoryUserResponseDTO>>> getUserCategories(){
        List<CategoryUserResponseDTO> categoryUserResponseDTOS = getUserCategories.get();
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
