package com.exemple.demo.dtos;

import java.math.BigDecimal;
import java.sql.Date;

public record OperadorDto(
  Integer  id,
  String nome,
  String email,
  BigDecimal salario,
  Date dataNascimento
) {}
