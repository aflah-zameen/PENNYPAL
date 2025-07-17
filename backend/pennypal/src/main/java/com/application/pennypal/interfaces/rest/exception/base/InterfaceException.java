package com.application.pennypal.interfaces.rest.exception.base;

import com.application.pennypal.interfaces.rest.exception.InterfaceErrorCode;

public class InterfaceException extends RuntimeException {
  private final String errorCode;
    public InterfaceException(String message, InterfaceErrorCode errorCode) {
        super(message);
        this.errorCode= errorCode.getValue();
    }

    public String getErrorCode(){
      return errorCode;
    }
}
