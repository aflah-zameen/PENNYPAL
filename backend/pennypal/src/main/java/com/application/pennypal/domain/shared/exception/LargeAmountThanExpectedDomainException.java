package com.application.pennypal.domain.shared.exception;

public class LargeAmountThanExpectedDomainException extends RuntimeException {
  public LargeAmountThanExpectedDomainException(String message) {
    super(message);
  }
}
