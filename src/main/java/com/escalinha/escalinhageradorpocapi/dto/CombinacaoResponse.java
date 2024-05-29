package com.escalinha.escalinhageradorpocapi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CombinacaoResponse {
    private List<List<ElementoDTO>> combinacao;
    private Double score;
}
