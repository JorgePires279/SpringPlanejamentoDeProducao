package com.exemple.demo.dtos;

import java.math.BigDecimal;
import java.time.Duration;

public record ProdutoDto(
  Integer  id,
  String nome,
  String tipo,
  Duration tempoProducao,
  BigDecimal custoMP
) {}

