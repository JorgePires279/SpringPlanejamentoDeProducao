package com.exemple.demo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateOSDto(
  @NotBlank(message = "O tipo da OP não pode ser vazio")
  String tipo,

  @NotNull(message = "ID da OP não pode ser nulo")
  Integer op,

  @NotNull(message = "Quantidade produzida não pode ser nula")
  Integer quantidadeProduzida,

  @NotNull(message = "ID do produto não pode ser nulo")
  Integer idProduto,

  @NotNull(message = "ID do operador não pode ser nulo")
  Integer idOperador,

  @NotNull(message = "Tempo de execução não pode ser nulo")
  Long tempoDeExecucaoEmSegundos
) {}