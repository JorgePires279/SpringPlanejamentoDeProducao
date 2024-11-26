
package com.exemple.demo.services;

import java.util.List;
import java.time.Duration;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.exemple.demo.dtos.CreateOPDto;
import com.exemple.demo.dtos.OPDto;
import com.exemple.demo.exceptions.NaoEncontradoException;
import com.exemple.demo.exceptions.RequisicaoErradaException;
import com.exemple.demo.mappers.OPMapper;
import com.exemple.demo.models.OP;
import com.exemple.demo.repositories.OPRepository;
import com.exemple.demo.repositories.ProdutoRepository;

@Service
public class OPService {

    private final OPRepository opRepository;
    private final ProdutoRepository produtoRepository;
    private final OPMapper opMapper;

    @Value("${app.tipos.ops}")
    private List<String> tiposPermitidos;

    public OPService(OPRepository opRepository, ProdutoRepository produtoRepository, OPMapper opMapper) {
        this.opRepository = opRepository;
        this.produtoRepository = produtoRepository;
        this.opMapper = opMapper;
    }

    public List<OPDto> getAllOP() {
        return opRepository.findAll().stream().map(opMapper::toDto).toList();
    }

    public OPDto getOPById(Integer id) {
        return opMapper.toDto(opRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("OP não encontrada")));
    }

    public OPDto createOP(CreateOPDto opDto) {

        if (!tiposPermitidos.contains(opDto.tipo())) {
            throw new RequisicaoErradaException("Tipo de OP inválido");
        }
    
    
        OP op = opMapper.toModel(opDto);

        preencherDadosAdicionais(op, opDto.quantidade());

        OP savedOp = opRepository.save(op);
    
        return new OPDto(
            savedOp.getId(),
            savedOp.getTipo(),
            savedOp.getProduto(),
            op.getNomeProduto(),
            savedOp.getQuantidade(),
            savedOp.getTempoProducao(),
            savedOp.getCustoMPProduto()
        );
    }

    public OPDto updateOP(Integer id, OPDto opDto) {

        OP op = opRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("OP não encontrada"));

        if (opDto.tipo() != null) op.setTipo(opDto.tipo());
        if (opDto.produto() != null) op.setProduto(opDto.produto());
        if (opDto.quantidade() != null) op.setQuantidade(opDto.quantidade());
        if (opDto.tempoProducao() != null) op.setTempoProducao(opDto.tempoProducao());
    
        preencherDadosAdicionais(op, opDto.quantidade());
    
        OP savedOp = opRepository.save(op);
    
        return new OPDto(
            savedOp.getId(),
            savedOp.getTipo(),
            savedOp.getProduto(),
            op.getNomeProduto(),
            savedOp.getQuantidade(),
            savedOp.getTempoProducao(),
            savedOp.getCustoMPProduto()
        );
    }

    public OPDto deleteOP(Integer id) {
        OP op = opRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("OP não encontrada"));
        opRepository.delete(op);
        return opMapper.toDto(op);
    }

    private void preencherDadosAdicionais(OP op, Integer quantidade) {

        var produto = produtoRepository.findById(op.getProduto()).orElseThrow(() -> new NaoEncontradoException("Produto não encontrado"));

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

