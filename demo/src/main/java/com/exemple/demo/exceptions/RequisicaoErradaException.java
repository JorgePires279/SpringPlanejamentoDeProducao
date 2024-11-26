package com.exemple.demo.exceptions;

import org.springframework.http.HttpStatus;

public class RequisicaoErradaException extends ServiceException{
  public RequisicaoErradaException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }  
}
