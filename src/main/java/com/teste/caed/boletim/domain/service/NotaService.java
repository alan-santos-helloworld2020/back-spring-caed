package com.teste.caed.boletim.domain.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.teste.caed.boletim.domain.model.Aluno;
import com.teste.caed.boletim.domain.model.Avaliacao;
import com.teste.caed.boletim.domain.model.Nota;
import com.teste.caed.boletim.domain.repository.AlunoRepository;
import com.teste.caed.boletim.domain.repository.AvaliacaoRepository;
import com.teste.caed.boletim.domain.repository.NotaRepository;
import com.teste.caed.boletim.web.dto.MediaAlunoDTO;
import com.teste.caed.boletim.web.dto.NotaDTO;
import com.teste.caed.boletim.web.dto.NotaUpsertDTO;

import jakarta.transaction.Transactional;

@Service
public class NotaService {
    private final NotaRepository notaRepo;
    private final AlunoRepository alunoRepo;
    private final AvaliacaoRepository avaliacaoRepo;

    public NotaService(NotaRepository notaRepo, AlunoRepository alunoRepo,
            AvaliacaoRepository avaliacaoRepo) {
        this.notaRepo = notaRepo;
        this.alunoRepo = alunoRepo;
        this.avaliacaoRepo = avaliacaoRepo;
    }

    @Transactional
    public List<NotaDTO> upsertLote(List<NotaUpsertDTO> lote) {
        if (lote == null || lote.isEmpty())
            return List.of();
        var alunoIds = lote.stream().map(NotaUpsertDTO::alunoId).collect(Collectors.toSet());
        var avaliacaoIds = lote.stream().map(NotaUpsertDTO::avaliacaoId).collect(Collectors.toSet());
        var existentes = notaRepo.findByAlunoIdInAndAvaliacaoIdIn(alunoIds,
                avaliacaoIds);
        Map<String, Nota> idx = new HashMap<>();
        for (Nota n : existentes) {
            idx.put(n.getAluno().getId() + ":" + n.getAvaliacao().getId(), n);
        }
        List<Nota> toSave = new ArrayList<>();
        for (NotaUpsertDTO dto : lote) {
            String key = dto.alunoId() + ":" + dto.avaliacaoId();
            Nota n = idx.get(key);
            if (n == null) {
                n = new Nota();
                Aluno a = alunoRepo.findById(dto.alunoId()).orElseThrow();
                Avaliacao av = avaliacaoRepo.findById(dto.avaliacaoId()).orElseThrow();
                n.setAluno(a);
                n.setAvaliacao(av);
            }
            n.setValor(dto.valor());
            toSave.add(n);
        }
        var saved = notaRepo.saveAll(toSave);
        return saved.stream()
                .map(x -> new NotaDTO(x.getId(), x.getAluno().getId(),
                        x.getAvaliacao().getId(), x.getValor()))
                .toList();
    }

    public List<MediaAlunoDTO> calcularMedias(Long turmaId, Long disciplinaId) {
        var alunos = alunoRepo.findByTurmaId(turmaId);
        var avals = avaliacaoRepo.findByDisciplinaId(disciplinaId);
        if (alunos.isEmpty() || avals.isEmpty())
            return List.of();
        Map<Long, BigDecimal> somaPonderada = new HashMap<>();
        Map<Long, Integer> somaPesos = new HashMap<>();
        var alunoIds = alunos.stream().map(Aluno::getId).toList();
        var avIds = avals.stream().map(Avaliacao::getId).toList();
        var notas = notaRepo.findByAlunoIdInAndAvaliacaoIdIn(alunoIds, avIds);
        for (Nota n : notas) {
            long alunoId = n.getAluno().getId();
            int peso = n.getAvaliacao().getPeso();
            BigDecimal contrib = n.getValor().multiply(BigDecimal.valueOf(peso));
            somaPonderada.merge(alunoId, contrib, BigDecimal::add);
            somaPesos.merge(alunoId, peso, Integer::sum);
        }
        List<MediaAlunoDTO> out = new ArrayList<>();
        for (Aluno a : alunos) {
            Integer pesos = somaPesos.get(a.getId());
            if (pesos == null || pesos == 0) {
                out.add(new MediaAlunoDTO(a.getId(), null));
            } else {
                BigDecimal media = somaPonderada.get(a.getId())
                        .divide(BigDecimal.valueOf(pesos), 2,
                                java.math.RoundingMode.HALF_UP);
                out.add(new MediaAlunoDTO(a.getId(), media));
            }
        }
        return out;
    }
}