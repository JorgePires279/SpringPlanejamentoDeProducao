package com.exemple.demo.models;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Operador {
  @Id 
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operador_seq") 
  @SequenceGenerator(name = "operador_seq", sequenceName = "operador_seq", initialValue = 1, allocationSize = 1) 
  private Integer id;
  private String nome;
  private String email;
  private BigDecimal salario;
  private Date dataNascimento;

  public Operador() {
  }

  public Operador(String nome, String email, BigDecimal salario, Date dataNascimento) {
    this.nome = nome;
    this.email = email;
    this.salario = salario;
    this.dataNascimento = dataNascimento;
  }

    @OneToMany(mappedBy = "operador")
    private List<OS> ordensDeServico;

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
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public BigDecimal getSalario() {
    return salario;
  }
  public void setSalario(BigDecimal salario) {
    this.salario = salario;
  }
  public Date getDataNascimento() {
    return dataNascimento;
  }
  public void setDataNascimento(Date dataNascimento) {
    this.dataNascimento = dataNascimento;
  }
}
