package com.exemple.demo.dtos;

import java.time.LocalDateTime;

public record ErroDto(
    LocalDateTime timestamp,
    int status,
    String erro,
    String mensagem
) {}
