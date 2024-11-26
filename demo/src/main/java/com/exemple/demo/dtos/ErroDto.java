package com.exemple.demo.dtos;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public record ErroDto(
  LocalDateTime timestamp,
  HttpStatus erro,
  String mensagem
) {}
