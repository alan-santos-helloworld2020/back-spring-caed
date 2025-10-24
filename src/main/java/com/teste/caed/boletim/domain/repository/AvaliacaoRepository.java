package com.teste.caed.boletim.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teste.caed.boletim.domain.model.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
  List<Avaliacao> findByDisciplinaId(Long disciplinaId);
}