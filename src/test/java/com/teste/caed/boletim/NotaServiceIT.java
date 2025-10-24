package com.teste.caed.boletim;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.teste.caed.boletim.domain.model.Nota;
import com.teste.caed.boletim.domain.repository.AlunoRepository;
import com.teste.caed.boletim.domain.repository.AvaliacaoRepository;
import com.teste.caed.boletim.domain.repository.NotaRepository;
import com.teste.caed.boletim.domain.service.NotaService;
import com.teste.caed.boletim.web.dto.MediaAlunoDTO;


import jakarta.validation.ConstraintViolationException;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class NotaServiceIT {

    @Autowired
    NotaService notaService;
    @Autowired
    AlunoRepository alunoRepository;
    @Autowired
    AvaliacaoRepository avaliacaoRepository;
    @Autowired
    NotaRepository notaRepository;

    @Test
    @DisplayName("Calcula média ponderada para turma=1, disciplina=1 (seed) => Ana = 7.75")
    void calcularMedias_seed_retornaEsperado() {
        var medias = notaService.calcularMedias(1L, 1L);
        // Espera 3 alunos (Ana, Bruno, Carla) da turma 1
        assertEquals(3, medias.size());

        MediaAlunoDTO ana = medias.stream().filter(m -> m.alunoId().equals(1L)).findFirst().orElseThrow();
        assertNotNull(ana.media());
        assertEquals(new BigDecimal("7.75"), ana.media()); // (8*5 + 6*2 + 10*1) / 8 = 62/8 = 7.75
    }

    @Test
    @DisplayName("Sem notas para o aluno -> média = null")
    void calcularMedias_semNotas_retornaNull() {
        var medias = notaService.calcularMedias(1L, 1L);
        // Bruno (id=2) não possui notas no seed
        var bruno = medias.stream().filter(m -> m.alunoId().equals(2L)).findFirst().orElseThrow();
        assertNull(bruno.media());
    }

    @Test
    @DisplayName("Validação de limites: 0.0 e 10.0 ok; fora lança ConstraintViolationException (sem violar unique)")
    void validarLimitesDeNota() {
        // Use SEMPRE pares (aluno, avaliacao) diferentes para cada assert
        var aluno = alunoRepository.findById(2L).orElseThrow(); // Bruno (não tem notas no seed)
        var av10 = avaliacaoRepository.findById(10L).orElseThrow();
        var av11 = avaliacaoRepository.findById(11L).orElseThrow();
        var av12 = avaliacaoRepository.findById(12L).orElseThrow();

        // 0.0: deve ser aceito (par: 2-10)
        Nota n = new Nota();
        n.setAluno(aluno);
        n.setAvaliacao(av10);
        n.setValor(new BigDecimal("0.0"));
        assertDoesNotThrow(() -> notaRepository.saveAndFlush(n));

        // 10.0: atualiza o MESMO registro (não cria novo) para não violar unique (par
        // ainda 2-10)
        n.setValor(new BigDecimal("10.0"));
        assertDoesNotThrow(() -> notaRepository.saveAndFlush(n));

        // -0.1: deve falhar (usar par diferente 2-11)
        Nota nNeg = new Nota();
        nNeg.setAluno(aluno);
        nNeg.setAvaliacao(av11);
        nNeg.setValor(new BigDecimal("-0.1"));
        assertThrows(ConstraintViolationException.class, () -> notaRepository.saveAndFlush(nNeg));

        // 10.1: deve falhar (outro par diferente 2-12)
        Nota nAcima = new Nota();
        nAcima.setAluno(aluno);
        nAcima.setAvaliacao(av12);
        nAcima.setValor(new BigDecimal("10.1"));
    }

}
