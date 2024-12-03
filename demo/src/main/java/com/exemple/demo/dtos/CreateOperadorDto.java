package com.exemple.demo.dtos;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateOperadorDto(
  @Size(min = 3, max = 256, message = "O nome do operador deve ter entre 3 e 256 caracteres")
  String nome,
  
  @NotBlank(message = "O email não pode ser vazio")
  String email,
  
  @Min(value = 256, message = "O salário deve ser no mínimo 256")
  @Max(value = 99999, message = "O salário deve ser no máximo 99999")
  BigDecimal salario,
  
  @Past(message = "A data de nascimento deve ser anterior à data atual")
  Date dataNascimento
) {}