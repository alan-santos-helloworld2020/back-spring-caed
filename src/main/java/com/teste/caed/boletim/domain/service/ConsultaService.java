package com.teste.caed.boletim.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.teste.caed.boletim.domain.model.Aluno;
import com.teste.caed.boletim.domain.model.Avaliacao;
import com.teste.caed.boletim.domain.model.Disciplina;
import com.teste.caed.boletim.domain.model.Turma;
import com.teste.caed.boletim.domain.repository.AlunoRepository;
import com.teste.caed.boletim.domain.repository.AvaliacaoRepository;
import com.teste.caed.boletim.domain.repository.DisciplinaRepository;
import com.teste.caed.boletim.domain.repository.NotaRepository;
import com.teste.caed.boletim.domain.repository.TurmaRepository;
import com.teste.caed.boletim.web.dto.AlunoDTO;
import com.teste.caed.boletim.web.dto.AvaliacaoDTO;
import com.teste.caed.boletim.web.dto.NotaDTO;

@Service
public class ConsultaService {
    private final TurmaRepository turmaRepo;
    private final DisciplinaRepository disciplinaRepo;
    private final AlunoRepository alunoRepo;
    private final AvaliacaoRepository avaliacaoRepo;
    private final NotaRepository notaRepo;

    public ConsultaService(TurmaRepository turmaRepo, DisciplinaRepository disciplinaRepo,
            AlunoRepository alunoRepo, AvaliacaoRepository avaliacaoRepo,
            NotaRepository notaRepo) {
        this.turmaRepo = turmaRepo;
        this.disciplinaRepo = disciplinaRepo;
        this.alunoRepo = alunoRepo;
        this.avaliacaoRepo = avaliacaoRepo;
        this.notaRepo = notaRepo;
    }

    public List<Turma> listarTurmas() {
        return turmaRepo.findAll();
    }

    public List<Disciplina> listarDisciplinas() {
        return disciplinaRepo.findAll();
    }

    public List<AlunoDTO> alunosPorTurma(Long turmaId) {
        return alunoRepo.findByTurmaId(turmaId).stream()
                .map(a -> new AlunoDTO(a.getId(), a.getNome(), a.getTurma().getId()))
                .collect(Collectors.toList());
    }

    public List<AvaliacaoDTO> avaliacoesPorDisciplina(Long disciplinaId) {
        return avaliacaoRepo.findByDisciplinaId(disciplinaId).stream()
                .map(av -> new AvaliacaoDTO(av.getId(), av.getTitulo(), av.getPeso(), av.getDisciplina().getId()))
                .collect(Collectors.toList());
    }

    public List<NotaDTO> notasExistentes(Long turmaId, Long disciplinaId) {
        var alunos = alunoRepo.findByTurmaId(turmaId);
        var avals = avaliacaoRepo.findByDisciplinaId(disciplinaId);
        var alunoIds = alunos.stream().map(Aluno::getId).toList();
        var avIds = avals.stream().map(Avaliacao::getId).toList();
        return notaRepo.findByAlunoIdInAndAvaliacaoIdIn(alunoIds, avIds).stream()
                .map(n -> new NotaDTO(n.getId(), n.getAluno().getId(), n.getAvaliacao().getId(), n.getValor()))
                .collect(Collectors.toList());
    }
}