package com.application.pennypal.interfaces.rest.controllers;

import com.application.pennypal.application.dto.input.goal.GoalAdminFilter;
import com.application.pennypal.application.dto.output.goal.AdminGoalResponseOutput;
import com.application.pennypal.application.dto.output.goal.GoalWithdrawOutput;
import com.application.pennypal.application.dto.output.paged.PagedResultOutput;
import com.application.pennypal.application.dto.output.user.UserFiltersOutput;
import com.application.pennypal.application.port.in.admin.FetchUsers;
import com.application.pennypal.application.port.in.category.*;
import com.application.pennypal.application.port.in.goal.*;
import com.application.pennypal.application.port.in.user.GetUser;
import com.application.pennypal.domain.catgeory.entity.Category;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.domain.user.valueObject.Roles;
import com.application.pennypal.domain.valueObject.GoalStatus;
import com.application.pennypal.infrastructure.adapter.out.goal.GoalWithdrawApprovalInfraService;
import com.application.pennypal.interfaces.rest.dtos.ApiResponse;
import com.application.pennypal.application.dto.output.goal.AdminGoalStats;
import com.application.pennypal.interfaces.rest.dtos.goal.GoalAdminResponseDTO;
import com.application.pennypal.interfaces.rest.dtos.goal.PaginatedGoalResponse;
import com.application.pennypal.interfaces.rest.dtos.goal.PaginationInfo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/private/admin")
public class AdminController {

    private  final UserRepositoryPort userRepository;
    private final FetchUsers fetchUsers;
    private final GetUser getUser;
    private final CreateCategory createCategory;
    private final GetCategories getCategories;
    private final UpdateCategory updateCategory;
    private final ToggleCategoryStatus toggleCategoryStatus;
    private final DeleteCategory deleteCategory;
    private final GetAdminGoalData getAdminGoalData;
    private final GetAdminGoalStats getAdminGoalStats;
    private final GoalWithdrawApprovalInfraService goalWithdrawApprovalInfraService;
    private final GoalWithdrawalRejection goalWithdrawalRejection;
    private final GetAllGoalWithdrawRequests getAllGoalWithdrawRequests;

    //user management
    @GetMapping("fetch-filtered-users")
    public ResponseEntity<ApiResponse<PagedResultOutput<User>>> getFilteredUsers(
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "10") int size,
            @RequestParam(name = "status",required = false) Boolean status,
            @RequestParam(name = "keyword",required = false) String keyword,
            @RequestParam(name = "role",required = false) String role,
            @RequestParam(name = "joinedAfter",required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime joinedAfter,
            @RequestParam(name = "joinedBefore",required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime joinedBefore
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
        PagedResultOutput<User> userPagedResultOutput = fetchUsers.execute(new UserFiltersOutput(
                enumRole,joinedBefore,joinedAfter,status
        ),page,size,keyword
                );
        return ResponseEntity.ok(new ApiResponse<>(true, userPagedResultOutput,"Users details fetched successfully"));
    }

    @PatchMapping("/users/{email}/toggle-active")
    public ResponseEntity<User> toggleUserActive(
            @PathVariable String email,
            @RequestParam boolean active
    ) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(active){
            user = user.activate();
        }
        else{
            user= user.deactivate();
        }
        userRepository.update(user);
        return ResponseEntity.ok(user);
    }

    //category management
//    @PostMapping("/add-category")
//    public ResponseEntity<ApiResponse<Category>> addNewCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO, HttpServletRequest httpServletRequest){
//            UserDomainDTO user= getUser.get(extractTokenFromCookie(httpServletRequest,"accessToken"));
//            Category category = Category.builder()
//                    .name(categoryRequestDTO.name())
//                    .description(categoryRequestDTO.description())
//                    .sortOrder(categoryRequestDTO.sortOrder())
//                    .usageTypes(Arrays.stream(categoryRequestDTO.usageTypes()).map(String::toUpperCase).map(CategoryType::valueOf).toList())
//                    .usageCount(categoryRequestDTO.usageCount())
//                    .color(categoryRequestDTO.color())
//                    .icon(categoryRequestDTO.icon())
//                    .isDefault(categoryRequestDTO.isDefault())
//                    .isActive(categoryRequestDTO.active())
//                    .build();
//            category = createCategory.execute(category, user.id());
//            return ResponseEntity.ok(new ApiResponse<>(true,category,"Successfully category created"));
//    }

    @GetMapping("/get-categories")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories(){
        List<Category> categories = getCategories.execute();
        return ResponseEntity.ok(new ApiResponse<>(true,categories,"All categories fetched successfully"));
    }

//    @PutMapping("/update-category/{id}")
//    public ResponseEntity<ApiResponse<Category>> updateCategory(
//            @RequestBody CategoryRequestDTO categoryRequestDTO,
//            @PathVariable("id") Long categoryId,HttpServletRequest request){
//        String token = extractTokenFromCookie(request,"accessToken");
//        UserDomainDTO user = getUser.get(token);
//        Category category = Category.builder()
//                .name(categoryRequestDTO.name())
//                .description(categoryRequestDTO.description())
//                .sortOrder(categoryRequestDTO.sortOrder())
//                .usageTypes(Arrays.stream(categoryRequestDTO.usageTypes()).map(String::toUpperCase).map(CategoryType::valueOf).toList())
//                .usageCount(categoryRequestDTO.usageCount())
//                .color(categoryRequestDTO.color())
//                .icon(categoryRequestDTO.icon())
//                .isDefault(categoryRequestDTO.isDefault())
//                .isActive(categoryRequestDTO.active())
//                .build();
//         category = updateCategory.update(category,categoryId, user.id());
//         return ResponseEntity.ok(new ApiResponse<>(true,category,"Updated successfully"));
//    }

//    @PatchMapping("/toggle-category-status/{id}")
//    public ResponseEntity<ApiResponse<Category>> updateCategory(@PathVariable("id") Long categoryId,HttpServletRequest request){
//            String token = extractTokenFromCookie(request,"accessToken");
//            UserDomainDTO userDomainDTO = getUser.get(token);
//            Category category = toggleCategoryStatus.toggle(categoryId,userDomainDTO.id());
//            return ResponseEntity.ok(new ApiResponse<>(true, category,"Updated category status"));
//    }

    @DeleteMapping("/delete-category/{id}")
    public ResponseEntity<ApiResponse<?>> deleteCategory(@PathVariable("id") String categoryId){
        deleteCategory.delete(categoryId);
        return ResponseEntity.ok(new ApiResponse<>(true,null,"Deleted successfully"));
    }


    /// Goal management
    @GetMapping("/goals")
    public ResponseEntity<ApiResponse<PaginatedGoalResponse>> getPaginatedGoals(@RequestParam(name = "page",defaultValue = "0") int page,
                                                                                @RequestParam(name = "size",defaultValue = "10") int size,
                                                                                @RequestParam(name = "status",required = false) String status,
                                                                                @RequestParam(name = "keyword",required = false) String keyword,
                                                                                @RequestParam(name = "category",required = false) String categoryId,
                                                                                @RequestParam(name = "dateFrom",required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                                                                                @RequestParam(name = "dateTo",required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
                                                                                @RequestParam(name = "minAmount",required = false)BigDecimal minAmount,
                                                                                @RequestParam(name = "maxAmount",required = false) BigDecimal maxAmount,
                                                                                @RequestParam(name = "asc",defaultValue = "false",required = false) boolean asc
                                                                                ){
        PagedResultOutput<AdminGoalResponseOutput> resultOutput = getAdminGoalData.execute(
                new GoalAdminFilter(
                        keyword,
                        status != null ? GoalStatus.valueOf(status.toUpperCase()) : null,
                        dateFrom,
                        dateTo,
                        categoryId,
                        minAmount,
                        maxAmount,
                        asc
                                ),page,size);
        PaginatedGoalResponse goalResponse = new PaginatedGoalResponse(
                resultOutput.content().stream()
                        .map(output -> new GoalAdminResponseDTO(
                                output.goalId(),
                                output.user(),
                                output.title(),
                                output.targetAmount(),
                                output.currentAmount(),
                                output.status(),
                                output.createdAt(),
                                output.endDate(),
                                output.lastContributed(),
                                output.category(),
                                output.description()
                        ))
                        .toList(),
                new PaginationInfo(
                      resultOutput.pageNumber(),
                        resultOutput.pageSize(),
                        resultOutput.totalElements(),
                        resultOutput.totalPages()
                )
        );
        return ResponseEntity.ok(new ApiResponse<>(true,goalResponse,"Fetched successfully"));
    }

    @GetMapping("/goals/stats")
    public ResponseEntity<ApiResponse<AdminGoalStats>> getAdminGoalStats(){
        AdminGoalStats adminGoalStats = getAdminGoalStats.execute();
        return ResponseEntity.ok(new ApiResponse<>(true,adminGoalStats,"Goal stats fetched successfully"));
    }

    @PostMapping("/goals/withdrawals/{id}/approve")
    public ResponseEntity<ApiResponse<?>> approveWithdrawals(@PathVariable("id") String withdrawId){
        goalWithdrawApprovalInfraService.approveWithdrawRequest(withdrawId);
        return ResponseEntity.ok(new ApiResponse<>(true,null,"Withdraw approved successfully"));
    }

    @PostMapping("/goals/withdrawals/{id}/reject")
    public ResponseEntity<ApiResponse<?>> rejectWithdrawals(@PathVariable("id") String withdrawId){
        goalWithdrawalRejection.execute(withdrawId);
        return ResponseEntity.ok(new ApiResponse<>(true,null,"Withdraw Rejected successfully"));
    }

    @GetMapping("/goals/withdrawals")
    public ResponseEntity<ApiResponse<List<GoalWithdrawOutput>>> getWithdrawRequests(){
        List<GoalWithdrawOutput> outputList = getAllGoalWithdrawRequests.execute();
        return ResponseEntity.ok(new ApiResponse<>(true,outputList,"Withdraw requests fetched successfully"));
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
