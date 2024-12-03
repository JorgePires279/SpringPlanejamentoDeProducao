package com.exemple.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.exemple.demo.dtos.CreateProdutoDto;
import com.exemple.demo.dtos.ProdutoDto;
import com.exemple.demo.exceptions.ErroConflitoException;
import com.exemple.demo.exceptions.ErroNaoEncontradoException;
import com.exemple.demo.exceptions.ErroRequisicaoErradaException;
import com.exemple.demo.mappers.ProdutoMapper;
import com.exemple.demo.models.Produto;
import com.exemple.demo.repositories.ProdutoRepository;
import com.exemple.demo.repositories.OPRepository;

@Service
public class ProdutoService {

  private final ProdutoRepository produtoRepository;
  private final OPRepository opRepository;
  private final ProdutoMapper produtoMapper;

  @Value("${app.tipos.produtos}")
  private List<String> tiposPermitidos;

  public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper,OPRepository opRepository ) {
    this.produtoRepository = produtoRepository;
    this.opRepository = opRepository;
    this.produtoMapper = produtoMapper;
  }

  public Page<ProdutoDto> getProdutos(Pageable pageable) {
    Page<Produto> produtos = produtoRepository.findAllProduto(pageable);

    if (produtos.isEmpty()) {
      throw new ErroConflitoException("Não há produtos disponíveis na página solicitada.");
    }

    return produtos.map(produtoMapper::toDto);
  }


  public List<ProdutoDto> getProdutosByTipo(String tipo) {

    if (!tiposPermitidos.contains(tipo)) {
        throw new ErroRequisicaoErradaException("Tipo de produto inválido");
    }

    List<Produto> produtos = produtoRepository.findByTipo(tipo);
    if (produtos.isEmpty()) {
        throw new ErroNaoEncontradoException("Nenhum produto encontrado para o tipo: " + tipo);
    }

    return produtos.stream().map(produtoMapper::toDto).collect(Collectors.toList());
  }

  public List<ProdutoDto> getAllProdutos() {
    return produtoRepository.findAll().stream().map(produtoMapper::toDto).toList();
  }

  public ProdutoDto getProdutoById(Integer id) {
    return produtoMapper.toDto(produtoRepository.findById(id).orElseThrow(() -> new ErroNaoEncontradoException("Produto não encontrado")));
  }

  public ProdutoDto createProduto(CreateProdutoDto produtoDto) {
    if (!tiposPermitidos.contains(produtoDto.tipo())) {
      throw new ErroRequisicaoErradaException("Tipo de produto inválido");
    }

    produtoRepository.findByNome(produtoDto.nome()).ifPresent(
      produto -> {
        throw new ErroConflitoException("Produto já cadastrado");
      }
    );

    return produtoMapper.toDto(produtoRepository.save(produtoMapper.toModel(produtoDto)));

  }

  public ProdutoDto updateProduto(Integer id, ProdutoDto produtoDto) {

    Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ErroNaoEncontradoException("Produto não encontrada"));

    if (produtoDto.nome() != null) produto.setNome(produtoDto.nome());
    if (produtoDto.tipo() != null) produto.setTipo(produtoDto.tipo());
    if (produtoDto.tempoProducao() != null) produto.setTempoProducao(produtoDto.tempoProducao());
    if (produtoDto.custoMP() != null) produto.setCustoMP(produtoDto.custoMP());

    if (!tiposPermitidos.contains(produto.getTipo())) {
      throw new ErroRequisicaoErradaException("Tipo de produto inválido");
    }

    produtoRepository.findByNome(produto.getNome()).ifPresent(
      produtoCadastrado -> {
        if (!produtoCadastrado.getId().equals(produto.getId())) {
          throw new ErroConflitoException("Produto já cadastrada");
        }
      }
    );

    return produtoMapper.toDto(produtoRepository.save(produto));
  }

  public ProdutoDto deleteProduto(Integer id) {

    Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ErroNaoEncontradoException("Produto não encontrado"));

    var produtoEmUso = opRepository.findById(id).orElse(null);
    if (produtoEmUso != null) {
        throw new ErroConflitoException("Produto não pode ser deletado, pois está em uso.");
    }

    produtoRepository.delete(produto);
    return produtoMapper.toDto(produto);
  }

}

