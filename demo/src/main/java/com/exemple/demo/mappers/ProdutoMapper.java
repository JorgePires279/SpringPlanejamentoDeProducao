package com.exemple.demo.mappers;

import org.springframework.stereotype.Component;
import com.exemple.demo.dtos.CreateProdutoDto;
import com.exemple.demo.dtos.ProdutoDto;
import com.exemple.demo.models.Produto;

@Component
public class ProdutoMapper {
  public ProdutoDto toDto(Produto produto) {
    return new ProdutoDto(
      produto.getId(),
      produto.getNome(),
      produto.getTipo(),
      produto.getTempoProducao(),
      produto.getCustoMP()
    );
  }

  public Produto toModel(CreateProdutoDto produtoDto) {
    return new Produto(
      produtoDto.nome(),
      produtoDto.tipo(),
      produtoDto.tempoProducao(),
      produtoDto.custoMP()
    );
  }
}
