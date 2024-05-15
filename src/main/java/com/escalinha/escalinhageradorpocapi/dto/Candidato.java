package com.escalinha.escalinhageradorpocapi.dto;

import jakarta.validation.constraints.NotBlank;

public record Candidato(
        @NotBlank
        String id
) {
}
