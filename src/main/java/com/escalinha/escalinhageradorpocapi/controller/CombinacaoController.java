package com.escalinha.escalinhageradorpocapi.controller;

import com.escalinha.escalinhageradorpocapi.dto.CombinacaoRequest;
import com.escalinha.escalinhageradorpocapi.dto.CombinacaoResponse;
import com.escalinha.escalinhageradorpocapi.service.ProcessamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/combinacoes")
@Tag(name = "Combinações dos elementos entre as posições")
public class CombinacaoController {

    @Autowired
    private ProcessamentoService service;

    @PostMapping
    @Operation(summary = "Gera as combinações dos elementos e retorna os melhores resultados")
    public List<CombinacaoResponse> combinar(
            @Valid
            @RequestBody
            CombinacaoRequest escala) {

        return service.processar(escala);
    }
}
