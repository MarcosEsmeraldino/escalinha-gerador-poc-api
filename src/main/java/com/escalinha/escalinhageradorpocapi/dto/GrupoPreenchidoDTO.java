package com.escalinha.escalinhageradorpocapi.dto;

import java.util.List;

public record GrupoPreenchidoDTO(
        String id,
        List<CandidatoDTO> candidatos
) {
}
