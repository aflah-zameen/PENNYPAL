package com.application.pennypal.application.exception.usecase.auth;

public class UserSuspendedApplicationException extends RuntimeException {
  public UserSuspendedApplicationException(String message) {
    super(message);
  }
}
