package com.escalinha.escalinhageradorpocapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record Escala(

        @NotBlank
        String id,

        @Positive
        List<Grupo> grupos,

        @NotEmpty
        @Size(min = 2)
        List<Candidato> candidatos
) {
}
