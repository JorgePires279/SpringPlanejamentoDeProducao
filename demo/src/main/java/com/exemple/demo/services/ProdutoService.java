package com.exemple.demo.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.exemple.demo.dtos.CreateProdutoDto;
import com.exemple.demo.dtos.ProdutoDto;
import com.exemple.demo.exceptions.ConflitoException;
import com.exemple.demo.exceptions.NaoEncontradoException;
import com.exemple.demo.exceptions.RequisicaoErradaException;
import com.exemple.demo.mappers.ProdutoMapper;
import com.exemple.demo.models.Produto;
import com.exemple.demo.repositories.ProdutoRepository;

@Service
public class ProdutoService {

  private final ProdutoRepository produtoRepository;
  private final ProdutoMapper produtoMapper;

  @Value("${app.tipos.produtos}")
  private List<String> tiposPermitidos;

  public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
    this.produtoRepository = produtoRepository;
    this.produtoMapper = produtoMapper;
  }

  public List<ProdutoDto> getAllProdutos() {
    return produtoRepository.findAll().stream().map(produtoMapper::toDto).toList();
  }

  public ProdutoDto getProdutoById(Integer id) {
    return produtoMapper.toDto(produtoRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("Produto não encontrada")));
  }

  public ProdutoDto createProduto(CreateProdutoDto produtoDto) {
    if (!tiposPermitidos.contains(produtoDto.tipo())) {
      throw new RequisicaoErradaException("Tipo de produto inválido");
    }

    produtoRepository.findByNome(produtoDto.nome()).ifPresent(
      produto -> {
        throw new ConflitoException("Produto já cadastrada");
      }
    );

    return produtoMapper.toDto(produtoRepository.save(produtoMapper.toModel(produtoDto)));

  }

  public ProdutoDto updateProduto(Integer id, ProdutoDto produtoDto) {

    Produto produto = produtoRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("Produto não encontrada"));

    if (produtoDto.nome() != null) produto.setNome(produtoDto.nome());
    if (produtoDto.tipo() != null) produto.setTipo(produtoDto.tipo());
    if (produtoDto.tempoProducao() != null) produto.setTempoProducao(produtoDto.tempoProducao());
    if (produtoDto.custoMP() != null) produto.setCustoMP(produtoDto.custoMP());

    if (!tiposPermitidos.contains(produto.getTipo())) {
      throw new RequisicaoErradaException("Tipo de produto inválido");
    }

    produtoRepository.findByNome(produto.getNome()).ifPresent(
      produtoCadastrado -> {
        if (!produtoCadastrado.getId().equals(produto.getId())) {
          throw new ConflitoException("Produto já cadastrada");
        }
      }
    );

    return produtoMapper.toDto(produtoRepository.save(produto));
  }

  public ProdutoDto deleteProduto(Integer id) {

    Produto produto = produtoRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("Produto não encontrada"));

    produtoRepository.delete(produto);
    return produtoMapper.toDto(produto);
    
  }
}

