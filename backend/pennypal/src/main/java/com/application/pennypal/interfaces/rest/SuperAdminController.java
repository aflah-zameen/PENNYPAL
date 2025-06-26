package com.application.pennypal.interfaces.rest;

import com.application.pennypal.application.auth.AuthService;
import com.application.pennypal.application.dto.PagedResult;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.interfaces.rest.dtos.AdminRegisterRequest;
import com.application.pennypal.interfaces.rest.dtos.ApiResponse;
import com.application.pennypal.interfaces.rest.dtos.RegisterResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/superadmin")
public class SuperAdminController {

    private final AuthService authService;

    SuperAdminController(AuthService authService){
        this.authService = authService;
    }
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerAdmin(@Valid @RequestBody AdminRegisterRequest request){
        authService.registerAdmin(request);
        return ResponseEntity.ok(new RegisterResponse(true, "Admin Registered Successfully"));
    }

//    @GetMapping("/fetch-filtered-user")
//    public ResponseEntity<PagedResult<User>> fetchFilteredUsers(){
//        return
//    }
    @GetMapping("/empty")
    public String getEmpty(){
        return "empty";
    }
}
