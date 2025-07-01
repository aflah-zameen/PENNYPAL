package com.application.pennypal.interfaces.rest;

import com.application.pennypal.application.dto.UserUpdateApplicationDTO;
import com.application.pennypal.application.port.TokenServicePort;
import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.application.usecases.Income.AddIncome;
import com.application.pennypal.application.usecases.Income.GetAllIncomes;
import com.application.pennypal.application.usecases.Income.GetTotalIncome;
import com.application.pennypal.application.usecases.user.GetUser;
import com.application.pennypal.application.usecases.user.UpdateUser;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.user.valueObject.IncomeDTO;
import com.application.pennypal.domain.user.valueObject.UserDomainDTO;
import com.application.pennypal.infrastructure.adapter.persistence.jpa.entity.UserEntity;
import com.application.pennypal.interfaces.rest.dtos.ApiResponse;
import com.application.pennypal.interfaces.rest.dtos.IncomeRequest;
import com.application.pennypal.interfaces.rest.dtos.UpdateUserRequest;
import com.application.pennypal.shared.exception.UserNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final TokenServicePort tokenServicePort;
    private final GetUser getUser;
    private final GetTotalIncome getTotalIncome;
    private final GetAllIncomes getAllIncomes;

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

    @PostMapping("/add-income")
    public ResponseEntity<ApiResponse<IncomeDTO>> addIncome(@Valid @RequestBody IncomeRequest incomeRequest,HttpServletRequest request){
        String token =  extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        IncomeDTO incomeDTO = addIncome.add(new IncomeDTO(incomeRequest.amount(),incomeRequest.source(),
                LocalDate.parse(incomeRequest.income_date()),"PENDING",null,incomeRequest.notes()),user.id());
        return ResponseEntity.ok(new ApiResponse<>(true,incomeDTO,"Income added successfully"));
    }

    @GetMapping("/total-income")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalIncome(HttpServletRequest request){
        String token =  extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        BigDecimal totalIncome = getTotalIncome.get(user.id(),LocalDate.now());
        return ResponseEntity.ok(new ApiResponse<>(true,totalIncome,"Total income fetched successfully"));
    }

    @GetMapping("/fetch-incomes")
    public ResponseEntity<ApiResponse<List<IncomeDTO>>> getAllIncomes(HttpServletRequest request){
        String token = extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        List<IncomeDTO> incomeDTOList = getAllIncomes.get(user.id());
        return ResponseEntity.ok(new ApiResponse<>(true,incomeDTOList,"Fetch all incomes successfully"));
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
