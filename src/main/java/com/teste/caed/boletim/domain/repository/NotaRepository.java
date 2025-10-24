package com.teste.caed.boletim.domain.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teste.caed.boletim.domain.model.Nota;

public interface NotaRepository extends JpaRepository<Nota, Long> {
  Optional<Nota> findByAlunoIdAndAvaliacaoId(Long alunoId, Long avaliacaoId);
  List<Nota> findByAlunoIdInAndAvaliacaoIdIn(Collection<Long> alunoIds, Collection<Long> avaliacaoIds);
}