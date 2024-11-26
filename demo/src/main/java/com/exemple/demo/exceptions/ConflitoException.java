package com.exemple.demo.exceptions;

import org.springframework.http.HttpStatus;

public class ConflitoException extends ServiceException{
  public ConflitoException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }  
}
