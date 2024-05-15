package com.escalinha.escalinhageradorpocapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GrupoDTO(

        @NotBlank
        String id,

        @NotNull
        @Min(1)
        Integer tamanho
) {
}
