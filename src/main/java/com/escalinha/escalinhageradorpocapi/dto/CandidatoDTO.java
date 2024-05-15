package com.escalinha.escalinhageradorpocapi.dto;

import jakarta.validation.constraints.NotBlank;

public record CandidatoDTO(
        @NotBlank
        String id
) {
}
