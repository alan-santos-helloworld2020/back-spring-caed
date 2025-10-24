package com.teste.caed.boletim.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teste.caed.boletim.domain.model.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
  List<Aluno> findByTurmaId(Long turmaId);
}