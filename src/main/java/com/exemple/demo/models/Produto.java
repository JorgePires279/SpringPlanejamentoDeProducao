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
public class Produto {
  @Id 
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produto_seq") 
  @SequenceGenerator(name = "produto_seq", sequenceName = "produto_seq", initialValue = 1, allocationSize = 1) 
  private Integer  id;
  private String nome;
  private String tipo;
  @Convert(converter = DurationConverter.class)
  private Duration tempoProducao;
  private BigDecimal custoMP;

  public Produto() {
  }

  public Produto(String nome, String tipo, Duration tempoProducao, BigDecimal custoMP) {
    this.nome = nome;
    this.tipo = tipo;
    this.tempoProducao = tempoProducao;
    this.custoMP = custoMP;
  }

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }
  public String getTipo() {
    return tipo;
  }
  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
  public Duration getTempoProducao() {
    return tempoProducao;
  }
  public void setTempoProducao(Duration tempoProducao) {
    this.tempoProducao = tempoProducao;
  }
  public BigDecimal getCustoMP() {
    return custoMP;
  }
  public void setCustoMP(BigDecimal custoMP) {
    this.custoMP = custoMP;
  }

    public String getTempoProducaoFormatado() {
      long horas = tempoProducao.toHours();
      long minutos = tempoProducao.toMinutesPart();
      return horas + " horas e " + minutos + " minutos";
  }

  public void setTempoProducaoHorasMinutos(long horas, long minutos) {
      this.tempoProducao = Duration.ofHours(horas).plusMinutes(minutos);
  }

  
}
