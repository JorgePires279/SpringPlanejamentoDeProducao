package com.exemple.demo.dtos;

import java.time.Duration;

public record OSDto(
  Integer id,
  String tipo,
  Integer op,
  Integer quantidadeOp,
  Integer quantidadeOS,
  Integer idProduto,
  String nomeProduto,
  Integer idOperador,
  String nomeOperador,
  Duration tempoDeExecucao,
  String produtividade
) {}