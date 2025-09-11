package com.application.pennypal.interfaces.rest.controllers;

import com.application.pennypal.application.dto.input.AnalyticsFilters;
import com.application.pennypal.application.dto.input.coin.RedemptionFilterInputModel;
import com.application.pennypal.application.dto.input.goal.GoalAdminFilter;
import com.application.pennypal.application.dto.input.subscription.SubscriptionPlanInputModel;
import com.application.pennypal.application.dto.output.coin.PaginatedRedemptionRequest;
import com.application.pennypal.application.dto.output.coin.PaginationRedemptionInfo;
import com.application.pennypal.application.dto.output.coin.RedemptionHistoryOutputModel;
import com.application.pennypal.application.dto.output.coin.RedemptionStatsOutputModel;
import com.application.pennypal.application.dto.output.goal.AdminGoalResponseOutput;
import com.application.pennypal.application.dto.output.goal.GoalWithdrawOutput;
import com.application.pennypal.application.dto.output.lend.LoanAdminSummary;
import com.application.pennypal.application.dto.output.lend.LoanCaseOutputModel;
import com.application.pennypal.application.dto.output.lend.LoanOutputModel;
import com.application.pennypal.application.dto.output.paged.PagedResultOutput;
import com.application.pennypal.application.dto.output.reward.RewardPolicyOutputModel;
import com.application.pennypal.application.dto.output.sale.PaymentStatusDTO;
import com.application.pennypal.application.dto.output.sale.SalesDataOutput;
import com.application.pennypal.application.dto.output.sale.SubscriptionAnalyticsOutput;
import com.application.pennypal.application.dto.output.sale.SubscriptionBreakdownOutput;
import com.application.pennypal.application.dto.output.subscription.PlanOutputModel;
import com.application.pennypal.application.dto.output.user.UserFiltersOutput;
import com.application.pennypal.application.port.in.admin.FetchUsers;
import com.application.pennypal.application.port.in.admin.UpdateAdmin;
import com.application.pennypal.application.port.in.category.*;
import com.application.pennypal.application.port.in.coin.ApproveRedemptionRequest;
import com.application.pennypal.application.port.in.coin.GetRedemptionRequests;
import com.application.pennypal.application.port.in.coin.GetRedemptionStats;
import com.application.pennypal.application.port.in.coin.RejectRedemptionRequest;
import com.application.pennypal.application.port.in.goal.*;
import com.application.pennypal.application.port.in.lent.AdminLoanReminder;
import com.application.pennypal.application.port.in.lent.GetAllLoans;
import com.application.pennypal.application.port.in.lent.GetLoanAdminSummary;
import com.application.pennypal.application.port.in.lent.LoanFilterInputModel;
import com.application.pennypal.application.port.in.reward.*;
import com.application.pennypal.application.port.in.sale.SubscriptionAnalytics;
import com.application.pennypal.application.port.in.transaction.GetAllLoanCases;
import com.application.pennypal.application.port.in.user.GetUser;
import com.application.pennypal.application.service.sale.SubscriptionAnalyticsService;
import com.application.pennypal.domain.LoanCase.CaseActionType;
import com.application.pennypal.domain.catgeory.entity.Category;
import com.application.pennypal.domain.coin.RedemptionRequestStatus;
import com.application.pennypal.domain.reward.RewardActionType;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.domain.user.valueObject.Roles;
import com.application.pennypal.domain.valueObject.GoalStatus;
import com.application.pennypal.domain.valueObject.UserDomainDTO;
import com.application.pennypal.infrastructure.adapter.out.coin.ApproveRedemptionRequestInfraService;
import com.application.pennypal.infrastructure.adapter.out.goal.GoalWithdrawApprovalInfraService;
import com.application.pennypal.infrastructure.adapter.out.lent.UpdateCaseActionInfraService;
import com.application.pennypal.infrastructure.adapter.out.subscription.SubscriptionInfraService;
import com.application.pennypal.interfaces.rest.dtos.ApiResponse;
import com.application.pennypal.application.dto.output.goal.AdminGoalStats;
import com.application.pennypal.interfaces.rest.dtos.goal.GoalAdminResponseDTO;
import com.application.pennypal.interfaces.rest.dtos.goal.PaginatedGoalResponse;
import com.application.pennypal.interfaces.rest.dtos.goal.PaginationInfo;
import com.application.pennypal.interfaces.rest.dtos.lent.LoanFilterDto;
import com.application.pennypal.interfaces.rest.dtos.reward.AddRewardPolicyDTO;
import com.application.pennypal.interfaces.rest.dtos.subscription.AddSubscriptionPlanDTO;
import com.application.pennypal.interfaces.rest.dtos.user.UpdateAdminRequest;
import com.application.pennypal.interfaces.rest.dtos.user.UpdateUserRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
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
    private final GetAllLoans getAllLoans;
    private final GetLoanAdminSummary getLoanAdminSummary;
    private final GetAllLoanCases getAllLoanCases;
    private final AdminLoanReminder adminLoanReminder;
    private final UpdateCaseActionInfraService updateCaseActionInfraService;
    private final SubscriptionInfraService subscriptionInfraService;
    private final SetReward setReward;
    private final DeleteRewardPolicy deleteRewardPolicy;
    private final ActivateRewardPolicy activateRewardPolicy;
    private final DeactivateRewardPolicy deactivateRewardPolicy;
    private final GetRewardPolicies getRewardPolicies;
    private final GetRedemptionStats getRedemptionStats;
    private final GetRedemptionRequests getRedemptionRequests;
    private final RejectRedemptionRequest rejectRedemptionRequest;
    private final ApproveRedemptionRequestInfraService approveRedemptionRequestInfraService;
    private final SubscriptionAnalytics subscriptionAnalyticsService;
    private final UpdateAdmin updateAdmin;


    @PutMapping("/update")
    public ResponseEntity<UserDomainDTO> updateAdmin(@ModelAttribute UpdateAdminRequest adminRequest,HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        user = updateAdmin.execute(user.userId(),adminRequest.name(),adminRequest.phone(),adminRequest.profile());
        return ResponseEntity.ok(user);
    }

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


    /// lending management
    @GetMapping("/lending/loan-history")
    public ResponseEntity<ApiResponse<List<LoanOutputModel>>> getAllLoan(){
        List<LoanOutputModel> outputModels = getAllLoans.execute();
        return ResponseEntity.ok(new ApiResponse<>(true,outputModels,"All loans fetched successfully"));
    }

    @PostMapping("/lending/loan-history/filter")
    public ResponseEntity<ApiResponse<List<LoanOutputModel>>> getFilteredLoans(@RequestBody LoanFilterDto loanFilterDto){
        List<LoanOutputModel> outputModels = getAllLoans.getFiltered(new LoanFilterInputModel(
                loanFilterDto.searchTerm(),
                loanFilterDto.status(),
                loanFilterDto.sortOrder()
        ));
        return ResponseEntity.ok(new ApiResponse<>(true,outputModels,"All loans fetched successfully"));
    }

    @GetMapping("/lending/summary-stats")
    public ResponseEntity<ApiResponse<LoanAdminSummary>> getAdminSummary(){
        LoanAdminSummary summary= getLoanAdminSummary.execute();
        return ResponseEntity.ok(new ApiResponse<>(true,summary,"Laon summary fetched successfully"));
    }


    /// cases
    @GetMapping("/lending/cases")
    public ResponseEntity<ApiResponse<List<LoanCaseOutputModel>>> getAllCases(){
        List<LoanCaseOutputModel>outputModels = getAllLoanCases.execute();
        return ResponseEntity.ok(new ApiResponse<>(true,outputModels,"Loan cases fetched successfully"));
    }

    @PostMapping("/lending/{id}/remind")
    public ResponseEntity<ApiResponse<?>> remindTheUser(@PathVariable("id") String id){
        adminLoanReminder.execute(id);
        return ResponseEntity.ok(new ApiResponse<>(true,null,"Reminded successfully"));
    }

    @PostMapping("/lending/users/{id}/suspend")
    public ResponseEntity<ApiResponse<?>> suspendTheUser(@PathVariable("id") String id, @RequestParam("caseId") String caseId){
        updateCaseActionInfraService.caseAction(id,caseId, CaseActionType.SUSPEND_USER);
        return ResponseEntity.ok(new ApiResponse<>(true,null,"User got suspended successfully"));
    }

    @PostMapping("/lending/{caseId}/cancel")
    public ResponseEntity<ApiResponse<?>> cancelCase(@PathVariable("caseId") String caseId){
        updateCaseActionInfraService.caseAction(null,caseId,CaseActionType.CANCEL_CASE);
        return ResponseEntity.ok(new ApiResponse<>(true ,null,"Case cancelled successfully"));
    }


    /// Subscription
    @GetMapping("/plans")
    public ResponseEntity<ApiResponse<List<PlanOutputModel>>> getAllPlans(){
        List<PlanOutputModel> outputModels = subscriptionInfraService.getPlans();
        return ResponseEntity.ok(new ApiResponse<>(true,outputModels,"All plans fetched successfully"));
    }

    @PostMapping("/plans/add")
    public ResponseEntity<ApiResponse<PlanOutputModel>> addNewPlan(@Valid @RequestBody AddSubscriptionPlanDTO addSubscriptionPlanDTO){
        PlanOutputModel outputModel = subscriptionInfraService.add(new SubscriptionPlanInputModel(
                addSubscriptionPlanDTO.name(),
                addSubscriptionPlanDTO.description(),
                addSubscriptionPlanDTO.amount(),
                addSubscriptionPlanDTO.durationDays(),
                addSubscriptionPlanDTO.features()
        ));
        return ResponseEntity.ok(new ApiResponse<>(true, outputModel,"Plan added successfully"));
    }

    @PutMapping("/plans/{planId}/edit")
    public ResponseEntity<ApiResponse<PlanOutputModel>> editNewPlan(@PathVariable("planId") String planId,@Valid @RequestBody AddSubscriptionPlanDTO addSubscriptionPlanDTO){
        PlanOutputModel outputModel = subscriptionInfraService.edit(planId,new SubscriptionPlanInputModel(
                addSubscriptionPlanDTO.name(),
                addSubscriptionPlanDTO.description(),
                addSubscriptionPlanDTO.amount(),
                addSubscriptionPlanDTO.durationDays(),
                addSubscriptionPlanDTO.features()
        ));
        return ResponseEntity.ok(new ApiResponse<>(true, outputModel,"Plan edited successfully"));

    }

    @DeleteMapping("/plans/{planId}/delete")
    public ResponseEntity<ApiResponse<PlanOutputModel>> deletePlan(@PathVariable("planId") String planId){
        PlanOutputModel outputModel = subscriptionInfraService.delete(planId);
        return ResponseEntity.ok(new ApiResponse<>(true, outputModel,"Plan deleted successfully"));
    }

    /// Reward management
    @PostMapping("/rewards/add")
    public ResponseEntity<ApiResponse<RewardPolicyOutputModel>> addRewardPolicy(@Valid @RequestBody AddRewardPolicyDTO policyDTO){
        RewardPolicyOutputModel outputModel = setReward.execute(RewardActionType.valueOf(policyDTO.actionType().toUpperCase()),policyDTO.coinAmount());
        return ResponseEntity.ok(new ApiResponse<>(true,outputModel,"New policy added successfully"));
    }

    @GetMapping("/rewards")
    public ResponseEntity<ApiResponse<List<RewardPolicyOutputModel>>> getAllPolicies(){
        List<RewardPolicyOutputModel> outputModels = getRewardPolicies.execute();
        return ResponseEntity.ok(new ApiResponse<>(true,outputModels,"All policies fetched successfully"));
    }

    @PutMapping("/rewards/{policyId}/edit")
    public ResponseEntity<ApiResponse<RewardPolicyOutputModel>> editRewardPolicy (@RequestBody AddRewardPolicyDTO policyDTO){
        RewardPolicyOutputModel outputModel = setReward.execute(RewardActionType.valueOf(policyDTO.actionType().toUpperCase()),policyDTO.coinAmount());
        return ResponseEntity.ok(new ApiResponse<>(true,outputModel,"Policy edited successfully"));
    }

    @DeleteMapping("/rewards/{policyId}/delete")
    public ResponseEntity<ApiResponse<RewardPolicyOutputModel>> deleteRewardPolicy (@PathVariable("policyId") String policyId){
        RewardPolicyOutputModel outputModel = deleteRewardPolicy.execute(policyId);
        return ResponseEntity.ok(new ApiResponse<>(true,outputModel,"Policy Deleted successfully"));
    }

    @PutMapping("/rewards/{policyId}/toggle-status")
    public ResponseEntity<ApiResponse<RewardPolicyOutputModel>> toggleRewardPolicy (@PathVariable("policyId") String policyId,@RequestParam boolean active){
        RewardPolicyOutputModel outputModel = null;
        if(active){
            outputModel = deactivateRewardPolicy.execute(policyId);
        }else{
            outputModel = activateRewardPolicy.execute(policyId);
        }
        return ResponseEntity.ok(new ApiResponse<>(true,outputModel,"Policy toggled successfully"));
    }


    /// redemptions
    @GetMapping("/redemptions")
    public ResponseEntity<ApiResponse<PaginatedRedemptionRequest>> getHistory(@RequestParam(defaultValue = "1") int page,
                                                                              @RequestParam(defaultValue = "10") int itemsPerPage,
                                                                              @RequestParam(required = false) String status,
//                                                                              @RequestParam(required = false) String search,
                                                                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                                                                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo){
        PaginatedRedemptionRequest outputModels = getRedemptionRequests.execute(new RedemptionFilterInputModel(
                page,
                itemsPerPage,
                status,
                dateFrom,
                dateTo

        ));
        return ResponseEntity.ok(new ApiResponse<>(true,outputModels,"Redemption requests fetched successfully"));

    }
    @GetMapping("/redemptions/stats")
    public ResponseEntity<ApiResponse<RedemptionStatsOutputModel>> getRedemptionStats()
    {
        RedemptionStatsOutputModel outputModel = getRedemptionStats.execute();
        return ResponseEntity.ok(new ApiResponse<>(true,outputModel,"Redemption stats fetched successfully"));
    }

    @PostMapping("/redemptions/{id}/approve")
    public  ResponseEntity<ApiResponse<?>> approveRedemptionRequest(@PathVariable("id") String redemptionId,HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        approveRedemptionRequestInfraService.approve(user.userId(),redemptionId);
        return ResponseEntity.ok(new ApiResponse<>(true,null,"Redemption request approved successfully"));
    }

    @PostMapping("/redemptions/{id}/reject")
    public  ResponseEntity<ApiResponse<?>> rejectRedemptionRequest(@PathVariable("id") String redemptionId,HttpServletRequest servletRequest){
        String token = extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        rejectRedemptionRequest.execute(user.userId(),redemptionId);
        return ResponseEntity.ok(new ApiResponse<>(true,null,"Redemption request rejected successfully"));
    }


    /// Sales analytics



    @GetMapping("/analytics/summary")
    public ResponseEntity<SubscriptionAnalyticsOutput> getAnalyticsSummary(@ModelAttribute AnalyticsFilters filters) {
        SubscriptionAnalyticsOutput summary = subscriptionAnalyticsService.getAnalyticsSummary(filters);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/analytics/sales")
    public ResponseEntity<List<SalesDataOutput>> getSalesData(@ModelAttribute AnalyticsFilters filters) {
        List<SalesDataOutput> salesData = subscriptionAnalyticsService.getSalesData(filters);
        return ResponseEntity.ok(salesData);
    }

    @GetMapping("/analytics/breakdown")
    public ResponseEntity<List<SubscriptionBreakdownOutput>> getSubscriptionBreakdown(@ModelAttribute AnalyticsFilters filters) {
        List<SubscriptionBreakdownOutput> breakdown = subscriptionAnalyticsService.getSubscriptionBreakdown(filters);
        return ResponseEntity.ok(breakdown);
    }

    @GetMapping("/analytics/payment-status-summary")
    public ResponseEntity<List<PaymentStatusDTO>> getPaymentStatusSummary(@ModelAttribute AnalyticsFilters filters) {
        List<PaymentStatusDTO> summary = subscriptionAnalyticsService.getPaymentStatusData(filters);
        return ResponseEntity.ok(summary);
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
