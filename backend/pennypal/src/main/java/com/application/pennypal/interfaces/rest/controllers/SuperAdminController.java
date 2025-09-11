package com.application.pennypal.interfaces.rest.controllers;

import com.application.pennypal.application.dto.output.user.RegisterOutputModel;
import com.application.pennypal.application.dto.output.user.UserOutputModel;
import com.application.pennypal.application.port.in.admin.CreateAdmin;
import com.application.pennypal.application.port.in.user.GetUser;
import com.application.pennypal.domain.valueObject.UserDomainDTO;
import com.application.pennypal.interfaces.rest.dtos.auth.RegisterRequestDTO;
import com.application.pennypal.interfaces.rest.dtos.auth.RegisterResponse;
import com.application.pennypal.interfaces.rest.dtos.auth.RegisterResponseDTO;
import com.application.pennypal.interfaces.rest.mappers.UserDtoMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneOffset;
import java.util.Arrays;

@RestController
@RequestMapping("/api/private/superadmin")
@RequiredArgsConstructor
public class SuperAdminController {

    private final GetUser getUser;
    private final CreateAdmin createAdmin;


    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/admin-register")
    public ResponseEntity<RegisterResponse> registerAdmin(@Valid @ModelAttribute RegisterRequestDTO requestDTO, HttpServletRequest servletRequest
    ){
        String token =  extractTokenFromCookie(servletRequest,"accessToken");
        UserDomainDTO user = getUser.get(token);
        RegisterOutputModel outputModel = createAdmin.execute(UserDtoMapper.toInput(requestDTO), user.userId());
        RegisterResponseDTO responseDTO = new RegisterResponseDTO(
                outputModel.id(),
                outputModel.email(),
                outputModel.expiry().toInstant(ZoneOffset.UTC)
        );
        return ResponseEntity.ok(new RegisterResponse(true,responseDTO,"Admin registered successfully"));
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
