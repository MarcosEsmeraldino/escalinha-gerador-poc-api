package com.escalinha.escalinhageradorpocapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record EscalaDTO(

        @NotBlank
        String id,

        @NotEmpty
        @Size(min = 1)
        List<@Valid GrupoDTO> grupos,

        @NotEmpty
        @Size(min = 2)
        List<@Valid CandidatoDTO> candidatos
) {
}
