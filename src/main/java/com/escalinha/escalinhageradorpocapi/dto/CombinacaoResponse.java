package com.escalinha.escalinhageradorpocapi.dto;

import java.util.List;

public record CombinacaoResponse(
        List<List<ElementoDTO>> combinacao,
        Integer sore
) {
}
