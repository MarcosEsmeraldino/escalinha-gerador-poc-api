package com.escalinha.escalinhageradorpocapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PosicaoDTO(

        @NotBlank
        String id,

        @NotNull
        @Positive
        Integer tamanho
) {
}
