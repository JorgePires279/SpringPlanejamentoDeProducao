package com.exemple.demo.dtos;

import java.math.BigDecimal;
import java.time.Duration;

public record OPDto(
    Integer id,
    String tipo,
    Integer produto,
    String nomeProduto,
    Integer quantidade,
    Duration tempoProducao,
    BigDecimal custoMPProduto
) {}
