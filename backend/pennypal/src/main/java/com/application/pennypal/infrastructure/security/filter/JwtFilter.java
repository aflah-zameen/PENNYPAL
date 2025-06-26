package com.application.pennypal.infrastructure.security.filter;

import com.application.pennypal.application.port.CheckUserBlockedPort;
import com.application.pennypal.domain.user.valueObject.Roles;
import com.application.pennypal.infrastructure.adapter.auth.CheckUserBlockedAdapter;
import com.application.pennypal.infrastructure.adapter.auth.JwtTokenServiceAdapter;
import com.application.pennypal.infrastructure.security.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.lang.Collections;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenServiceAdapter  jwtTokenServiceAdapter;
    private final CheckUserBlockedPort checkUserBlockedPort;
    public JwtFilter(JwtTokenServiceAdapter jwtTokenServiceAdapter, CheckUserBlockedPort checkUserBlockedPort){
        this.jwtTokenServiceAdapter = jwtTokenServiceAdapter;
        this.checkUserBlockedPort = checkUserBlockedPort;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = extractTokenFromCookie(request,"accessToken");
        String refreshToken = extractTokenFromCookie(request,"refreshToken");
        if(accessToken != null && !request.getRequestURI().contains("/login")){
            try{
                String email = jwtTokenServiceAdapter.getUsernameFromToken(accessToken);
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
                Set<String> roles = jwtTokenServiceAdapter.getRolesFromToken(accessToken);

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
        }
        if(accessToken == null && !request.getRequestURI().contains("/api/auth")){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
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
