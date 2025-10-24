package com.teste.caed.boletim.web.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

public record NotaUpsertDTO(Long alunoId, Long avaliacaoId,
        @DecimalMin("0.0") @DecimalMax("10.0") BigDecimal valor) {
}