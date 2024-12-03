package com.exemple.demo.exceptions;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.http.HttpStatus;

public class ExceptionResponse extends RuntimeException {
  private final LocalDateTime timestamp;
  private final int status;
  private final String erro;
  private final String mensagem;

  public ExceptionResponse(String mensagem, HttpStatus status) {
    this.timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    this.status = status.value();
    this.erro = status.getReasonPhrase();
    this.mensagem = mensagem;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public int getStatus() {
    return status;
  }

  public String getErro() {
    return erro;
  }

  public String getMensagem() {
    return mensagem;
  }
}