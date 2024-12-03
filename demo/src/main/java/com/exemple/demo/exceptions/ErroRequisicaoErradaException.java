package com.exemple.demo.exceptions;

import org.springframework.http.HttpStatus;

public class ErroRequisicaoErradaException extends ExceptionResponse{
  public ErroRequisicaoErradaException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }  
}
