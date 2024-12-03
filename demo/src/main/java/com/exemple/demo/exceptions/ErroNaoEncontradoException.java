package com.exemple.demo.exceptions;

import org.springframework.http.HttpStatus;

public class ErroNaoEncontradoException extends ExceptionResponse{
  public ErroNaoEncontradoException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }  
}
