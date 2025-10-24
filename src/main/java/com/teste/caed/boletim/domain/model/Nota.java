package com.teste.caed.boletim.domain.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

@Entity
@Table(name = "nota", uniqueConstraints = @UniqueConstraint(columnNames = { "aluno_id", "avaliacao_id" }))
public class Nota {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "aluno_id")
  private Aluno aluno;

  @ManyToOne(optional = false)
  @JoinColumn(name = "avaliacao_id")
  private Avaliacao avaliacao;

  @DecimalMin("0.0")
  @DecimalMax("10.0")
  @Column(nullable = false)
  private BigDecimal valor;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Aluno getAluno() {
    return aluno;
  }

  public void setAluno(Aluno aluno) {
    this.aluno = aluno;
  }

  public Avaliacao getAvaliacao() {
    return avaliacao;
  }

  public void setAvaliacao(Avaliacao avaliacao) {
    this.avaliacao = avaliacao;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }
}