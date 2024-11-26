package com.exemple.demo.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.exemple.demo.dtos.ErroDto;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(NaoEncontradoException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ErroDto handleNotFoundException(NaoEncontradoException e) {
    return new ErroDto(e.getTimestamp(), e.getStatus(), e.getMensagem());
  }

  @ExceptionHandler(ConflitoException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  @ResponseBody
  public ErroDto handleConflictException(ConflitoException e) {
    return new ErroDto(e.getTimestamp(), e.getStatus(), e.getMensagem());
  }

  @ExceptionHandler(RequisicaoErradaException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErroDto handleBadRequest(RequisicaoErradaException e) {
    return new ErroDto(e.getTimestamp(), e.getStatus(), e.getMensagem());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErroDto handleValidationException(MethodArgumentNotValidException e) {
    return new ErroDto(LocalDateTime.now(), HttpStatus.BAD_REQUEST, e.getAllErrors().get(0).getDefaultMessage());
  }
}
