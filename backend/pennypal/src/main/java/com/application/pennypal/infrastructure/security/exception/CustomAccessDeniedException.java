package com.application.pennypal.infrastructure.security.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedException implements AccessDeniedHandler {
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.setContentType("application/json");
      response.getWriter().write("{\"error\": \"Custom Access Denied: You do not have permission to access this resource.\"}");
  }
}
