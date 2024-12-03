package com.exemple.demo.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateOPDto(
    @NotBlank(message = "O tipo da OP não pode ser vazio")
    String tipo,

    @NotNull(message = "O código do produto não pode ser nulo")
    Integer produto,

    @NotNull(message = "A quantidade não pode ser nula")
    Integer quantidade
) {}