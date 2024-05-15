package com.escalinha.escalinhageradorpocapi.controller;

import com.escalinha.escalinhageradorpocapi.dto.EscalaDTO;
import com.escalinha.escalinhageradorpocapi.dto.EscalaPreenchidaDTO;
import com.escalinha.escalinhageradorpocapi.service.ProcessamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/processamentos")
@Tag(name = "Processamento das escalas")
public class ProcessamentoController {

    @Autowired
    private ProcessamentoService service;

    @PostMapping
    @Operation(summary = "Processa a escala e retorna os melhores resultados")
    public List<EscalaPreenchidaDTO> processar(
            @Valid
            @RequestBody
            EscalaDTO escala,

            @RequestParam(required = false)
            @Positive
            Integer limite) {

        return service.processar(escala, limite);
    }
}
