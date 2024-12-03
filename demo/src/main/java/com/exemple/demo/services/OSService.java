package com.exemple.demo.services;

import java.util.List;
import java.util.stream.Collectors;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.exemple.demo.dtos.CreateOSDto;
import com.exemple.demo.dtos.OSDto;
import com.exemple.demo.exceptions.ErroConflitoException;
import com.exemple.demo.exceptions.ErroNaoEncontradoException;
import com.exemple.demo.exceptions.ErroRequisicaoErradaException;
import com.exemple.demo.mappers.OSMapper;
import com.exemple.demo.models.OS;
import com.exemple.demo.repositories.OSRepository;
import com.exemple.demo.repositories.ProdutoRepository;
import com.exemple.demo.repositories.OperadorRepository;
import com.exemple.demo.repositories.OPRepository;

@Service
public class OSService {

  private final OSRepository osRepository;
  private final ProdutoRepository produtoRepository;
  private final OperadorRepository operadorRepository;
  private final OPRepository opRepository;
  private final OSMapper osMapper;
  
  @Value("${app.tipos.oss}")
  private List<String> tiposPermitidos;

  public OSService(OSRepository osRepository, ProdutoRepository produtoRepository, OperadorRepository operadorRepository, OPRepository opRepository, OSMapper osMapper) {
    this.osRepository = osRepository;
    this.produtoRepository = produtoRepository;
    this.operadorRepository = operadorRepository;
    this.opRepository = opRepository;
    this.osMapper = osMapper;
  }


  public Page<OSDto> getOS(Pageable pageable) {
    Page<OS> os = osRepository.findAllOS(pageable);

    if (os.isEmpty()) {
      throw new ErroRequisicaoErradaException("Não há OS's disponíveis na página solicitada.");
    }

    return os.map(osMapper::toDto);
  }


  public List<OSDto> getOSByTipo(String tipo) {

    if (!tiposPermitidos.contains(tipo)) {
        throw new ErroRequisicaoErradaException("Tipo de OS inválido");
    }

    List<OS> os = osRepository.findByTipo(tipo);
    if (os.isEmpty()) {
        throw new ErroNaoEncontradoException("Nenhuma OS encontrado para o tipo: " + tipo);
    }

    return os.stream().map(osMapper::toDto).collect(Collectors.toList());
  }


  public List<OSDto> getAllOS() {
    return osRepository.findAll().stream().map(osMapper::toDto).toList();
  }

  public OSDto getOSById(Integer id) {
    return osMapper.toDto(osRepository.findById(id).orElseThrow(() -> new ErroNaoEncontradoException("OS não encontrada")));
  }

  public OSDto createOS(CreateOSDto osDto) {
    if (!tiposPermitidos.contains(osDto.tipo())) {
      throw new ErroRequisicaoErradaException("Tipo de OS inválido");
    }
    OS os = osMapper.toModel(osDto);
    preencherDadosAdicionais(os);
    return osMapper.toDto(osRepository.save(os));
  }

  public OSDto updateOS(Integer id, OSDto osDto) {
    OS os = osRepository.findById(id).orElseThrow(() -> new ErroNaoEncontradoException("OS não encontrada"));

    if (osDto.tipo() != null) os.setTipo(osDto.tipo());
    if (osDto.op() != null) os.setOp(osDto.op());
    if (osDto.idProduto() != null) os.setIdProduto(osDto.idProduto());
    if (osDto.idOperador() != null) os.setIdOperador(osDto.idOperador());
    if (osDto.tempoDeExecucao() != null) os.setTempoDeExecucao(osDto.tempoDeExecucao());

    if (!tiposPermitidos.contains(os.getTipo())) {
      throw new ErroRequisicaoErradaException("Tipo de OS inválido");
    }

    preencherDadosAdicionais(os);

    return osMapper.toDto(osRepository.save(os));
  }

  public OSDto deleteOS(Integer id) {

    OS os = osRepository.findById(id).orElseThrow(() -> new ErroNaoEncontradoException("OS não encontrada"));

    osRepository.delete(os);
    return osMapper.toDto(os);
  }

  private void preencherDadosAdicionais(OS osDto) {
    
    var produto = produtoRepository.findById(osDto.getIdProduto()).orElseThrow(() -> new ErroNaoEncontradoException("Produto não encontrado"));

    String nomeProduto = produto.getNome();
    Duration tempoProducao = produto.getTempoProducao();

    var operador = operadorRepository.findById(osDto.getIdOperador()).orElseThrow(() -> new ErroNaoEncontradoException("Operador não encontrado"));

    String nomeOperador = operador.getNome();

    var op = opRepository.findById(osDto.getOp()).orElseThrow(() -> new ErroNaoEncontradoException("OP não encontrada"));
    
    Integer quantidadeOp = op.getQuantidade();

    if (osDto.getQuantidadeOS() > quantidadeOp) {
      throw new ErroConflitoException("Quantidade de OS não pode ser maior que a quantidade da OP");
    }

    Duration tempoDeExecucao = osDto.getTempoDeExecucao();

    if (tempoDeExecucao == null) {
        throw new IllegalArgumentException("Tempo de execução não encontrado.");
    }

    double tempoDeExecucaoEmSegundos = tempoDeExecucao.getSeconds();
    double tempoDeProducaoEmSegundos = tempoProducao.getSeconds();
    double produtividade = calcularProdutividade(tempoDeExecucaoEmSegundos, osDto.getQuantidadeOS(), tempoDeProducaoEmSegundos);

    osDto.setNomeProduto(nomeProduto);
    osDto.setNomeOperador(nomeOperador);
    osDto.setQuantidadeOp(quantidadeOp);
    osDto.setProdutividade(String.format("A produtividade da OS foi: %.2f %%", produtividade));
  }

  private double calcularProdutividade(double tempoDeExecucao, Integer quantidadeOS, double tempoProducao) {
  
    double produtiv = ((tempoProducao * quantidadeOS) / (tempoDeExecucao * quantidadeOS) * 100);

    return produtiv;
  }

}