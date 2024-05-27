package com.escalinha.escalinhageradorpocapi.dto;

import jakarta.validation.constraints.NotBlank;

public record ElementoDTO(
        @NotBlank
        String id
) {
}
