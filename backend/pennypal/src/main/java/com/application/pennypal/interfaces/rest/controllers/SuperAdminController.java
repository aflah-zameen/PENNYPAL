package com.application.pennypal.interfaces.rest.controllers;

import com.application.pennypal.application.auth.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/superadmin")
public class SuperAdminController {

    private final AuthService authService;

    SuperAdminController(AuthService authService){
        this.authService = authService;
    }
//    @PostMapping("/register")
//    public ResponseEntity<ApiResponse<String>> registerAdmin(@Valid @RequestBody AdminRegisterRequest request){
//        authService.registerAdmin(request);
//        return ResponseEntity.ok(new RegisterResponse(true, "Admin Registered Successfully"));
//    }

//    @GetMapping("/fetch-filtered-user")
//    public ResponseEntity<PagedResultOutput<User>> fetchFilteredUsers(){
//        return
//    }
    @GetMapping("/empty")
    public String getEmpty(){
        return "empty";
    }
}
