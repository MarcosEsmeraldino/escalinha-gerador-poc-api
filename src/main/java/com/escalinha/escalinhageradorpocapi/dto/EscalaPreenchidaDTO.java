package com.escalinha.escalinhageradorpocapi.dto;

import java.util.List;

public record EscalaPreenchidaDTO(
        String id,
        List<GrupoPreenchidoDTO> grupos,
        Integer sore
) {
}
