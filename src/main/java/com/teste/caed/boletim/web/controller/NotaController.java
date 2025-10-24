package com.teste.caed.boletim.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.teste.caed.boletim.domain.service.NotaService;
import com.teste.caed.boletim.web.dto.MediaAlunoDTO;
import com.teste.caed.boletim.web.dto.NotaDTO;
import com.teste.caed.boletim.web.dto.NotaUpsertDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/notas")
public class NotaController {
    private final NotaService service;

    public NotaController(NotaService service) {
        this.service = service;
    }

    @PostMapping("/lote")
    @ResponseStatus(HttpStatus.OK)
    public List<NotaDTO> salvarLote(@Valid @RequestBody List<NotaUpsertDTO> lote) {
        return service.upsertLote(lote);
    }

    @GetMapping("/medias")
    public List<MediaAlunoDTO> medias(@RequestParam Long turmaId, @RequestParam Long disciplinaId) {
        return service.calcularMedias(turmaId, disciplinaId);
    }
}