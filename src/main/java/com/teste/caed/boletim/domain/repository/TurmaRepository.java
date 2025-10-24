package com.teste.caed.boletim.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teste.caed.boletim.domain.model.Turma;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {

}