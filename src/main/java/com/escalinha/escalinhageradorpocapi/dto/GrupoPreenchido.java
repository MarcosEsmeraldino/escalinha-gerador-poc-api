package com.escalinha.escalinhageradorpocapi.dto;

import java.util.List;

public record GrupoPreenchido(
        String id,
        List<Candidato> candidatos
) {
}
