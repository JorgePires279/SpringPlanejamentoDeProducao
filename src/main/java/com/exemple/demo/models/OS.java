package com.exemple.demo.models;

import java.time.Duration;
import java.util.List;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

import com.exemple.demo.converters.DurationConverter;

@Entity
public class OS {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "os_seq")
  @SequenceGenerator(name = "os_seq", sequenceName = "os_seq", initialValue = 1, allocationSize = 1)
  private Integer id;
  private String tipo;
  @ManyToOne
  @JoinColumn(name = "OP")
  private Integer op;
  private Integer quantidadeOp;
  private Integer quantidadeOS;
  @ManyToOne
  @JoinColumn(name = "idProduto")
  private Integer idProduto;
  private String nomeProduto;
  @ManyToOne
  @JoinColumn(name = "idOperador")
  private Integer idOperador;
  private String nomeOperador;
  @Convert(converter = DurationConverter.class)
  private Duration tempoDeExecucao;
  private String produtividade;

  public OS() {}

  public OS(String tipo, Integer op, Integer quantidadeProduzida, Integer idProduto, Integer idOperador, Long tempoDeExecucaoEmSegundos) {
    this.tipo = tipo;
    this.op = op;
    this.quantidadeOS = quantidadeProduzida;
    this.idProduto = idProduto;
    this.idOperador = idOperador;
    this.tempoDeExecucao = Duration.ofSeconds(tempoDeExecucaoEmSegundos);
  }

  @OneToMany(mappedBy = "ops")
  private List<OS> ordensDeServico;

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

  public Integer getOp() {
    return op;
  }

  public void setOp(Integer op) {
    this.op = op;
  }

  public Integer getQuantidadeOp() {
    return quantidadeOp;
  }

  public void setQuantidadeOp(Integer quantidadeOp) {
    this.quantidadeOp = quantidadeOp;
  }

  public Integer getQuantidadeOS() {
    return quantidadeOS;
  }

  public void setQuantidadeOS(Integer quantidadeOS) {
    this.quantidadeOS = quantidadeOS;
  }

  public Integer getIdProduto() {
    return idProduto;
  }

  public void setIdProduto(Integer idProduto) {
    this.idProduto = idProduto;
  }

  public String getNomeProduto() {
    return nomeProduto;
  }

  public void setNomeProduto(String nomeProduto) {
    this.nomeProduto = nomeProduto;
  }

  public Integer getIdOperador() {
    return idOperador;
  }

  public void setIdOperador(Integer idOperador) {
    this.idOperador = idOperador;
  }

  public String getNomeOperador() {
    return nomeOperador;
  }

  public void setNomeOperador(String nomeOperador) {
    this.nomeOperador = nomeOperador;
  }

  public Duration getTempoDeExecucao() {
    return tempoDeExecucao;
  }

  public void setTempoDeExecucao(Duration tempoDeExecucao) {
    this.tempoDeExecucao = tempoDeExecucao;
  }

  public String getProdutividade() {
    return produtividade;
  }

  public void setProdutividade(String produtividade) {
    this.produtividade = produtividade;
  }
}
