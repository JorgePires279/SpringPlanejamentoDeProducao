
package com.exemple.demo.services;

import java.util.List;
import java.util.stream.Collectors;
import java.time.Duration;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.exemple.demo.dtos.CreateOPDto;
import com.exemple.demo.dtos.OPDto;
import com.exemple.demo.exceptions.ErroConflitoException;
import com.exemple.demo.exceptions.ErroNaoEncontradoException;
import com.exemple.demo.exceptions.ErroRequisicaoErradaException;
import com.exemple.demo.mappers.OPMapper;
import com.exemple.demo.models.OP;
import com.exemple.demo.repositories.OPRepository;
import com.exemple.demo.repositories.OSRepository;
import com.exemple.demo.repositories.ProdutoRepository;

@Service
public class OPService {

    private final OPRepository opRepository;
    private final OSRepository osRepository;
    private final ProdutoRepository produtoRepository;
    private final OPMapper opMapper;

    @Value("${app.tipos.ops}")
    private List<String> tiposPermitidos;

    public OPService(OPRepository opRepository,OSRepository osRepository, ProdutoRepository produtoRepository, OPMapper opMapper) {
        this.opRepository = opRepository;
        this.osRepository = osRepository;
        this.produtoRepository = produtoRepository;
        this.opMapper = opMapper;
    }

    public Page<OPDto> getOP(Pageable pageable) {
    Page<OP> op = opRepository.findAllOP(pageable);

    if (op.isEmpty()) {
      throw new ErroRequisicaoErradaException("Não há OP's disponíveis na página solicitada.");
    }

    return op.map(opMapper::toDto);
    }

    public List<OPDto> getOPByTipo(String tipo) {

        if (!tiposPermitidos.contains(tipo)) {
            throw new ErroRequisicaoErradaException("Tipo de OP inválido");
        }

        List<OP> op = opRepository.findByTipo(tipo);
        if (op.isEmpty()) {
            throw new ErroNaoEncontradoException("Nenhuma OP encontrado para o tipo: " + tipo);
        }

        return op.stream().map(opMapper::toDto).collect(Collectors.toList());
    }

    public List<OPDto> getAllOP() {
        return opRepository.findAll().stream().map(opMapper::toDto).toList();
    }

    public OPDto getOPById(Integer id) {
        return opMapper.toDto(opRepository.findById(id).orElseThrow(() -> new ErroNaoEncontradoException("OP não encontrada")));
    }

    public OPDto createOP(CreateOPDto opDto) {

        if (!tiposPermitidos.contains(opDto.tipo())) {
            throw new IllegalArgumentException("Tipo de OP inválido");
        }
    
    
        OP op = opMapper.toModel(opDto);

        preencherDadosAdicionais(op, opDto.quantidade());

        return opMapper.toDto(opRepository.save(op));
    }

    public OPDto updateOP(Integer id, OPDto opDto) {

        OP op = opRepository.findById(id).orElseThrow(() -> new ErroNaoEncontradoException("OP não encontrada"));

        if (opDto.tipo() != null) op.setTipo(opDto.tipo());
        if (opDto.produto() != null) op.setProduto(opDto.produto());
        if (opDto.quantidade() != null) op.setQuantidade(opDto.quantidade());
        if (opDto.tempoProducao() != null) op.setTempoProducao(opDto.tempoProducao());
    
        preencherDadosAdicionais(op, opDto.quantidade());
    
        return opMapper.toDto(opRepository.save(op));
    }

    public OPDto deleteOP(Integer id) {
        OP op = opRepository.findById(id).orElseThrow(() -> new ErroNaoEncontradoException("OP não encontrada"));

        var osEmUso = osRepository.findById(id).orElse(null);
        if (osEmUso != null) {
        throw new ErroConflitoException("OP não pode ser deletado, pois está em uso.");
        }

        opRepository.delete(op);
        return opMapper.toDto(op);
    }

    private void preencherDadosAdicionais(OP op, Integer quantidade) {

        var produto = produtoRepository.findById(op.getProduto()).orElseThrow(() -> new ErroNaoEncontradoException("Produto não encontrado"));

        String nomeProduto = produto.getNome();
    
        Duration tempoProducao = produto.getTempoProducao();
        Duration tempoTotalProducao = tempoProducao.multipliedBy(quantidade);
    
        BigDecimal custoMP = produto.getCustoMP();
        BigDecimal quantidadeBD = BigDecimal.valueOf(quantidade);
        BigDecimal custoTotalMPOP = custoMP.multiply(quantidadeBD);
    
        op.setTempoProducao(tempoTotalProducao);
        op.setNomeProduto(nomeProduto);
        op.setCustoMPProduto(custoTotalMPOP);
    }

}

