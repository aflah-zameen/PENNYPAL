package com.application.pennypal.interfaces.rest.controllers;

import com.application.pennypal.application.dto.input.lend.LendRequestInputModel;
import com.application.pennypal.application.dto.input.lend.LoanCaseInputModel;
import com.application.pennypal.application.dto.input.transaction.TransferInputModel;
import com.application.pennypal.application.dto.output.lend.*;
import com.application.pennypal.application.dto.output.user.TransferTransaction;
import com.application.pennypal.application.port.in.lent.*;
import com.application.pennypal.application.port.in.user.GetUser;
import com.application.pennypal.domain.valueObject.UserDomainDTO;
import com.application.pennypal.infrastructure.adapter.out.lent.*;
import com.application.pennypal.interfaces.rest.dtos.ApiResponse;
import com.application.pennypal.interfaces.rest.dtos.lent.CaseRequestDTO;
import com.application.pennypal.interfaces.rest.dtos.lent.LendingRequestDTO;
import com.application.pennypal.interfaces.rest.dtos.transaction.TransferRequestDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/private/user/lending")
@RequiredArgsConstructor
public class LentController {

    private final GetUser getUser;
    public final SendLendingRequestInfraService sendLendingRequestInfraService;
    private final GetLendingSummary getLendingSummary;
    private final GetLendingRequestsSent getLendingRequestsSent;
    private final GetLendingRequestReceived getLendingRequestReceived;
    private final GetLoansToRepay getLoansToRepay;
    private final GetLoansToCollect getLoansToCollect;
    private final LoanGrantInfraService loanGrantInfraService;
    private final RejectLendingRequest rejectLendingRequest;
    private final CancelLendingRequest cancelLendingRequest;
    private final RemindLoanPaymentInfraService remindLoanPayment;
    private final LoanRepaymentInfraService repaymentInfraService;
    private final FileLoanCaseInfraService fileLoanCaseInfraService;

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<LendingSummaryOutputModel>> getLendingSummary(HttpServletRequest servletRequest){
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        LendingSummaryOutputModel outputModel = getLendingSummary.execute(user.userId());
        return ResponseEntity.ok(new ApiResponse<>(true,outputModel,"Lending summary fetched successfully"));
    }

    @GetMapping("/requests/sent")
    public ResponseEntity<ApiResponse<List<LendingRequestOutputModel>>> getRequestsSend(HttpServletRequest servletRequest){
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        List<LendingRequestOutputModel> outputModels = getLendingRequestsSent.execute(user.userId());
        return ResponseEntity.ok(new ApiResponse<>(true,outputModels,"Lending requests sent fetched successfully"));
    }

    @GetMapping("/requests/received")
    public ResponseEntity<ApiResponse<List<LendingRequestOutputModel>>> getRequestsReceived(HttpServletRequest servletRequest){
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        List<LendingRequestOutputModel> outputModels = getLendingRequestReceived.execute(user.userId());
        return ResponseEntity.ok(new ApiResponse<>(true,outputModels,"Lending requests received fetched successfully"));
    }

    @GetMapping("/loans/to-pay")
    public ResponseEntity<ApiResponse<List<LoanOutputModel>>> getLoansToPay(HttpServletRequest servletRequest){
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        List<LoanOutputModel> outputModels = getLoansToRepay.execute(user.userId());
        return ResponseEntity.ok(new ApiResponse<>(true,outputModels,"Loans to pay fetched successfully"));
    }

    @GetMapping("/loans/to-collect")
    public ResponseEntity<ApiResponse<List<LoanOutputModel>>> getLoansToCollect(HttpServletRequest servletRequest){
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        List<LoanOutputModel> outputModels = getLoansToCollect.execute(user.userId());
        return ResponseEntity.ok(new ApiResponse<>(true,outputModels,"Loans to pay fetched successfully"));
    }

    @PostMapping("/requests/{id}/approve")
    public ResponseEntity<ApiResponse<TransferTransaction>> approveRequest(@Valid  @RequestBody TransferRequestDTO transferRequestDTO, @PathVariable("id") String requestId, HttpServletRequest servletRequest){
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        TransferTransaction transaction = loanGrantInfraService.grantLoan(user.userId(),requestId,new TransferInputModel(
                user.userId(),
                transferRequestDTO.recipientId(),
                transferRequestDTO.paymentMethodId(),
                "CARD",
                transferRequestDTO.amount(),
                transferRequestDTO.note(),
                transferRequestDTO.pin()
        ));
        return ResponseEntity.ok(new ApiResponse<>(true,transaction,"Loans amount transferred successfully"));
    }

    @PostMapping("/requests/{id}/reject")
    public ResponseEntity<ApiResponse<?>> rejectRequest(@PathVariable("id") String requestId, HttpServletRequest servletRequest){
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        rejectLendingRequest.execute(user.userId(),requestId);
        return ResponseEntity.ok(new ApiResponse<>(true,null,"Loans request rejected successfully"));
    }

    @PostMapping("/requests/{id}/cancel")
    public ResponseEntity<ApiResponse<?>> cancelRequest(@PathVariable("id") String requestId, HttpServletRequest servletRequest){
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        cancelLendingRequest.execute(user.userId(),requestId);
        return ResponseEntity.ok(new ApiResponse<>(true,null,"Loans request rejected successfully"));
    }


    @PostMapping("/request")
    public ResponseEntity<ApiResponse<LendingRequestOutputModel>> sendLendingRequest(@RequestBody LendingRequestDTO requestDTO,
                                                                                     HttpServletRequest servletRequest){
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        LendingRequestOutputModel outputModel = sendLendingRequestInfraService.send(user.userId(),new LendRequestInputModel(
                requestDTO.requestedTo(),
                requestDTO.amount(),
                requestDTO.message(),
                requestDTO.proposedDeadline()
        ));
        return ResponseEntity.ok(new ApiResponse<>(true,outputModel,"Request has initiated successfully"));
    }

    @PostMapping("/loans/{id}/remind")
    public ResponseEntity<ApiResponse<LoanOutputModel>> remindUser(@PathVariable("id") String loanId,HttpServletRequest servletRequest){
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        LoanOutputModel loanOutputModel = remindLoanPayment.remind(user,loanId);
        return ResponseEntity.ok(new ApiResponse<>(true,loanOutputModel,"Loan is reminded successfully"));
    }

    @PostMapping("/loan/{id}/repayment")
    public ResponseEntity<ApiResponse<RepayTransactionOutputModel>> loanRepayment(@PathVariable("id") String loanId,
                                                                          @Valid @RequestBody TransferRequestDTO requestDTO,
                                                                          HttpServletRequest servletRequest) {
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        RepayTransactionOutputModel transaction = repaymentInfraService.repay(user.userId(), loanId,new TransferInputModel(
                user.userId(),
                requestDTO.recipientId(),
                requestDTO.paymentMethodId(),
                "CARD",
                requestDTO.amount(),
                requestDTO.note(),
                requestDTO.pin()
        ));
        return ResponseEntity.ok(new ApiResponse<>(true,transaction,"Loans repayment transferred successfully"));
    }

    @PostMapping("/loans/file-case")
    public ResponseEntity<ApiResponse<LoanOutputModel>> fileCase(@Valid @RequestBody CaseRequestDTO caseRequestDTO,
                                                                     HttpServletRequest servletRequest){
        UserDomainDTO user = getUserFromRequest(servletRequest,"accessToken");
        LoanOutputModel outputModel = fileLoanCaseInfraService.execute(user.userId(),new LoanCaseInputModel(
                caseRequestDTO.loanId(),
                caseRequestDTO.reason()
        ));
        return ResponseEntity.ok(new ApiResponse<>(true,outputModel,"Loan case filed successfully"));
    }





    /// helper methods
    private UserDomainDTO getUserFromRequest(HttpServletRequest request, String tokenName){
        String accessToken = extractTokenFromCookie(request,tokenName);
        return getUser.get(accessToken);
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
