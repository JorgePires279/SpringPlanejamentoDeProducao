package com.exemple.demo.dtos;

import java.math.BigDecimal;
import java.time.Duration;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProdutoDto(
  @Size(min = 3, max = 256, message = "O nome do produto deve ter entre 3 e 256 caracteres") 
  String nome,

  @NotBlank(message = "O tipo do produto não pode ser vazio") 
  String tipo,

  @Min(value = 60, message = "O tempo de produção deve ser superior a 1 minuto") 
  @Max(value = 999 * 60, message = "O tempo de produção deve ser inferior a 999 minutos") 
  long tempoProducaoEmSegundos,
  
  @Min(value = 0, message = "O custo de matéria-prima não pode ser negativo") 
  BigDecimal custoMP
) {
  public Duration tempoProducao() {
    return Duration.ofSeconds(tempoProducaoEmSegundos);
  }
}
