package com.application.pennypal.interfaces.rest.controllers;

import com.application.pennypal.application.dto.input.lend.LendRequestInputModel;
import com.application.pennypal.application.dto.input.lend.LendingRequestStatusUpdateInputModel;
import com.application.pennypal.application.dto.output.lend.LendingRequestOutputModel;
import com.application.pennypal.application.dto.output.lend.LendingRequestStatusOutputModel;
import com.application.pennypal.application.port.in.user.GetUser;
import com.application.pennypal.domain.lend.LendingRequestStatus;
import com.application.pennypal.domain.valueObject.UserDomainDTO;
import com.application.pennypal.infrastructure.adapter.out.lend.SendLendingRequestInfraService;
import com.application.pennypal.infrastructure.adapter.out.lend.UpdateLendingRequestStatus;
import com.application.pennypal.interfaces.rest.dtos.ApiResponse;
import com.application.pennypal.interfaces.rest.dtos.lend.LendingRequestDTO;
import com.application.pennypal.interfaces.rest.dtos.lend.LendingRequestStatusDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestController
@RequestMapping("/api/private/user/lend")
@RequiredArgsConstructor
public class LendController {

    private final GetUser getUser;
    public final SendLendingRequestInfraService sendLendingRequestInfraService;
    private final UpdateLendingRequestStatus updateLendingRequestStatus;

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

    @PutMapping("/request/{id}/respond")
    public ResponseEntity<ApiResponse<LendingRequestStatusDto>> setTheRequestStatus(@RequestParam String requestId,
                                                                                    @RequestParam String status,
                                                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime acceptedDeadline){
        LendingRequestStatusOutputModel outputModel = updateLendingRequestStatus.execute(new LendingRequestStatusUpdateInputModel(
                requestId,
                LendingRequestStatus.valueOf(status.toUpperCase()),
                acceptedDeadline
        ));
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                new LendingRequestStatusDto
                        (
                               requestId,
                                outputModel.status().getValue(),
                               outputModel.acceptedDeadline()
                        ),
                "Update lending request status successfully"
                ));
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
