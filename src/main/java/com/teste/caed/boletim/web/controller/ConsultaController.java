package com.teste.caed.boletim.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teste.caed.boletim.domain.model.Disciplina;
import com.teste.caed.boletim.domain.model.Turma;
import com.teste.caed.boletim.domain.service.ConsultaService;
import com.teste.caed.boletim.web.dto.AlunoDTO;
import com.teste.caed.boletim.web.dto.AvaliacaoDTO;
import com.teste.caed.boletim.web.dto.LancamentosDTO;

@RestController
@RequestMapping
public class ConsultaController {
    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @GetMapping("/turmas")
    public List<Turma> turmas() {
        return service.listarTurmas();
    }

    @GetMapping("/disciplinas")
    public List<Disciplina> disciplinas() {
        return service.listarDisciplinas();
    }

    @GetMapping("/turmas/{turmaId}/alunos")
    public List<AlunoDTO> alunos(@PathVariable Long turmaId) {
        return service.alunosPorTurma(turmaId);
    }

    @GetMapping("/disciplinas/{disciplinaId}/avaliacoes")
    public List<AvaliacaoDTO> avaliacoes(@PathVariable Long disciplinaId) {
        return service.avaliacoesPorDisciplina(disciplinaId);
    }

    @GetMapping("/lancamentos")
    public LancamentosDTO lancamentos(@RequestParam Long turmaId, @RequestParam Long disciplinaId) {
        var alunos = service.alunosPorTurma(turmaId);
        var avals = service.avaliacoesPorDisciplina(disciplinaId);
        var notas = service.notasExistentes(turmaId, disciplinaId);
        return new LancamentosDTO(alunos, avals, notas);
    }
}