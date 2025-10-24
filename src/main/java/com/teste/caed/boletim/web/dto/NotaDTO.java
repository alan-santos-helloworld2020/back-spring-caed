package com.teste.caed.boletim.web.dto;

import java.math.BigDecimal;

public record NotaDTO(Long id, Long alunoId, Long avaliacaoId, BigDecimal valor) {
}