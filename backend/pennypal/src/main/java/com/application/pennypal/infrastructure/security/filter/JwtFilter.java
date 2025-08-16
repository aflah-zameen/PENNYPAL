package com.application.pennypal.infrastructure.security.filter;

import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.application.port.out.service.CheckUserBlockedPort;
import com.application.pennypal.application.port.out.service.TokenBlackListPort;
import com.application.pennypal.application.port.out.service.TokenServicePort;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.infrastructure.adapter.out.auth.JwtTokenServiceAdapter;
import com.application.pennypal.infrastructure.exception.InfraErrorCode;
import com.application.pennypal.infrastructure.exception.auth.InvalidAccessTokenInfrastructureException;
import com.application.pennypal.infrastructure.persistence.jpa.entity.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.lang.Collections;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenServicePort  tokenServicePort;
    private final TokenBlackListPort tokenBlackListPort;
    private final CheckUserBlockedPort checkUserBlockedPort;

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request){
        String path = request.getRequestURI();
        return !path.startsWith("/api/private");    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String accessToken = extractTokenFromCookie(request,"accessToken");
        if(accessToken == null || accessToken.isBlank() || !tokenServicePort.isValid(accessToken)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("timestamp", LocalDateTime.now().toString());
            errorBody.put("status", 401);
            errorBody.put("errorCode", InfraErrorCode.INVALID_ACCESS_TOKEN.code());
            errorBody.put("message", "The token is invalid");
            errorBody.put("path", ((HttpServletRequest) request).getRequestURI());

            new ObjectMapper().writeValue(response.getOutputStream(),errorBody);
            return;
        }
            try{
                String email = tokenServicePort.getUsernameFromToken(accessToken);
                if(checkUserBlockedPort.check(email)){
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json");

                    Map<String, Object> errorBody = new HashMap<>();
                    errorBody.put("timestamp", LocalDateTime.now().toString());
                    errorBody.put("status", 403);
                    errorBody.put("blocked",true);
                    errorBody.put("error", "Forbidden");
                    errorBody.put("message", "You have been blocked by the admin");
                    errorBody.put("path", ((HttpServletRequest) request).getRequestURI());

                    new ObjectMapper().writeValue(response.getOutputStream(),errorBody);
                    return;
                }
                Set<String> roles = tokenServicePort.getRolesFromToken(accessToken);

                List<SimpleGrantedAuthority> authorities = roles != null
                        ? roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_"+role))
                        .toList()
                        : Collections.emptyList();
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        email,null, authorities
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            catch(ExpiredJwtException ex){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } catch(JwtException ex){
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            catch(Exception ex){
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write(ex.getMessage());
                return ;
            }
        String refreshToken = extractTokenFromCookie(request,"refreshToken");
        if(refreshToken != null && (tokenBlackListPort.isBlacklisted(accessToken)||tokenBlackListPort.isBlacklisted(refreshToken))){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");

            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("timestamp", LocalDateTime.now().toString());
            errorBody.put("status", 401);
            errorBody.put("errorCode", InfraErrorCode.TOKEN_BLACKLISTED.code());
            errorBody.put("message", "Not able to access at the moment. Login again");
            errorBody.put("path", ((HttpServletRequest) request).getRequestURI());

            new ObjectMapper().writeValue(response.getOutputStream(),errorBody);
            return;
        }
        filterChain.doFilter(request,response);
    }

    private String extractTokenFromCookie(HttpServletRequest request,String tokenName) {
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
