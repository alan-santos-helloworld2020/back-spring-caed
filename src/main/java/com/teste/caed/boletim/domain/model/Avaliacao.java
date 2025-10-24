package com.teste.caed.boletim.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "avaliacao")
public class Avaliacao {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String titulo;

  @Min(1)
  @Max(5)
  private int peso;

  @ManyToOne(optional = false)
  @JoinColumn(name = "disciplina_id")
  private Disciplina disciplina;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public int getPeso() {
    return peso;
  }

  public void setPeso(int peso) {
    this.peso = peso;
  }

  public Disciplina getDisciplina() {
    return disciplina;
  }

  public void setDisciplina(Disciplina disciplina) {
    this.disciplina = disciplina;
  }
}