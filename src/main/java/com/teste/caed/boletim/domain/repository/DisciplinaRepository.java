package com.teste.caed.boletim.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teste.caed.boletim.domain.model.Disciplina;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

}