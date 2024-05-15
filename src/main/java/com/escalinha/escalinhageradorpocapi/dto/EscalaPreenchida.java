package com.escalinha.escalinhageradorpocapi.dto;

import java.util.List;

public record EscalaPreenchida(
        String id,
        List<GrupoPreenchido> grupos,
        Integer sore
) {
}
