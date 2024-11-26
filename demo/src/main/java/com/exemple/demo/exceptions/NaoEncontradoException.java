package com.exemple.demo.exceptions;

import org.springframework.http.HttpStatus;

public class NaoEncontradoException extends ServiceException{
  public NaoEncontradoException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }  
}
