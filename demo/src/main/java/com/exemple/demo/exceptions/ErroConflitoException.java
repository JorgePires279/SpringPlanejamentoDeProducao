package com.exemple.demo.exceptions;

import org.springframework.http.HttpStatus;

public class ErroConflitoException extends ExceptionResponse{
  public ErroConflitoException(String message) {
    super(message, HttpStatus.CONFLICT);
  }  
}
