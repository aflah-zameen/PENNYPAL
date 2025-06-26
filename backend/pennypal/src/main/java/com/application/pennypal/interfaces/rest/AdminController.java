package com.application.pennypal.interfaces.rest;

import com.application.pennypal.application.auth.AuthService;
import com.application.pennypal.application.dto.PagedResult;
import com.application.pennypal.application.dto.UserFiltersDTO;
import com.application.pennypal.application.usecases.admin.FetchUsers;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.domain.user.valueObject.Roles;
import com.application.pennypal.interfaces.rest.dtos.ApiResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private  final UserRepositoryPort userRepository;
    private final FetchUsers fetchUsers;

    AdminController(FetchUsers fetchUsers, UserRepositoryPort userRepository
                    ){
        this.userRepository = userRepository;
        this.fetchUsers = fetchUsers;
    }

    @GetMapping("fetch-filtered-users")
    public ResponseEntity<ApiResponse<PagedResult<User>>> getFilteredUsers(
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "10") int size,
            @RequestParam(name = "status",required = false) Boolean status,
            @RequestParam(name = "keyword",required = false) String keyword,
            @RequestParam(name = "role",required = false) String role,
            @RequestParam(name = "joinedAfter",required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime joinedAfter,
            @RequestParam(name = "joinedBefore",required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime joinedBefore
            ){
        Roles enumRole = null;
        if(role != null){
            enumRole= switch(role){
                case "USER" -> Roles.USER;
                case "ADMIN" -> Roles.ADMIN;
                case "SUPER_ADMIN" -> Roles.SUPER_ADMIN;
                default -> null;
            };
        }
        PagedResult<User> userPagedResult = fetchUsers.execute(new UserFiltersDTO(
                enumRole,joinedBefore,joinedAfter,status
        ),page,size,keyword
                );
        return ResponseEntity.ok(new ApiResponse<>(true,userPagedResult,"Users details fetched successfully"));
    }

    @PatchMapping("/users/{email}/toggle-active")
    public ResponseEntity<User> toggleUserActive(
            @PathVariable String email,
            @RequestBody Map<String, Boolean> request
    ) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(request.get("active"));
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}
