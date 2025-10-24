package com.teste.caed.boletim.web.dto;

import java.util.List;

public record LancamentosDTO(
        List<AlunoDTO> alunos,
        List<AvaliacaoDTO> avaliacoes,
        List<NotaDTO> notas) {
}