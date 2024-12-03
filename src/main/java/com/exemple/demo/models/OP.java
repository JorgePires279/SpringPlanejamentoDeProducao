package com.exemple.demo.models;

import java.math.BigDecimal;
import java.time.Duration;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

import com.exemple.demo.converters.DurationConverter;

@Entity
public class OP {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "op_seq")
    @SequenceGenerator(name = "op_seq", sequenceName = "op_seq", initialValue = 1, allocationSize = 1)
    private Integer id;
    private String tipo;
    private Integer produto;
    private String nomeProduto;
    private Integer quantidade;
    @Convert(converter = DurationConverter.class)
    private Duration tempoProducao;
    private BigDecimal custoMPProduto;

    public OP() {}

    public OP(String tipo, Integer produto, Integer quantidade) {
        this.tipo = tipo;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getProduto() {
        return produto;
    }

    public void setProduto(Integer produto) {
        this.produto = produto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Duration getTempoProducao() {
        return tempoProducao;
    }

    public void setTempoProducao(Duration tempoProducao) {
        this.tempoProducao = tempoProducao;
    }

    public BigDecimal getCustoMPProduto() {
        return custoMPProduto;
    }

    public void setCustoMPProduto(BigDecimal custoMPProduto) {
        this.custoMPProduto = custoMPProduto;
    }
}
