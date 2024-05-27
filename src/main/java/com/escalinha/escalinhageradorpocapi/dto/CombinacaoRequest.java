package com.escalinha.escalinhageradorpocapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CombinacaoRequest(

        @NotEmpty
        @Size(min = 1)
        List<@Valid PosicaoDTO> posicoes,

        @NotEmpty
        @Size(min = 2)
        List<@Valid ElementoDTO> elementos
) {
}
