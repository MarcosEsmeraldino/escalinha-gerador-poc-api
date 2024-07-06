package com.escalinha.escalinhageradorpocapi.utils;

import com.escalinha.escalinhageradorpocapi.dto.CombinacaoRequest;
import com.escalinha.escalinhageradorpocapi.dto.ElementoDTO;
import com.escalinha.escalinhageradorpocapi.dto.PosicaoDTO;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class CombinacaoRequestUtils {

    public static CombinacaoRequest getRequest(int posicoes, int tamanho, int elementos) {
        return new CombinacaoRequest(PosicaoUtils.getPosicoes(posicoes, tamanho), ElementoUtils.getElementos(elementos));
    }

    private static CombinacaoRequest getRequestCozinha() {
        var posicoes = List.of(
                new PosicaoDTO("02/06", 3),
                new PosicaoDTO("09/06", 3),
                new PosicaoDTO("16/06", 3),
                new PosicaoDTO("23/06", 3)
        );

        var elementos = List.of(
                new ElementoDTO("Ana"),
                new ElementoDTO("Ariete"),
                new ElementoDTO("Karina"),
                new ElementoDTO("Priscila"),
                new ElementoDTO("Gabriele"),
                new ElementoDTO("Nicolle"),
                new ElementoDTO("Aline"),
                new ElementoDTO("Luana")
        );
        return new CombinacaoRequest(posicoes, elementos);
    }
}
