package com.escalinha.escalinhageradorpocapi.controller;

import com.escalinha.escalinhageradorpocapi.dto.Escala;
import com.escalinha.escalinhageradorpocapi.dto.EscalaPreenchida;
import com.escalinha.escalinhageradorpocapi.service.ProcessamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/processamentos")
@Tag(name = "Processamento das escalas")
@Validated
public class ProcessamentoController {

    @Autowired
    private ProcessamentoService service;

    @PostMapping
    @Operation(summary = "Processa a escala e retorna os melhores resultados")
    public List<EscalaPreenchida> processar(
            @RequestBody
            Escala escala,

            @RequestParam(required = false)
            @Positive
            Integer limite) {

        return service.processar(escala, limite);
    }
}
