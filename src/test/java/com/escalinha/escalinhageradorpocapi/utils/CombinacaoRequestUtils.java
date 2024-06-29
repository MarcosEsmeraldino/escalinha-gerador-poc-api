package com.escalinha.escalinhageradorpocapi.utils;

import com.escalinha.escalinhageradorpocapi.dto.CombinacaoRequest;
import com.escalinha.escalinhageradorpocapi.dto.ElementoDTO;
import com.escalinha.escalinhageradorpocapi.dto.PosicaoDTO;

import java.util.ArrayList;
import java.util.List;

public class CombinacaoRequestUtils {

    public static CombinacaoRequest getRequest(int posicoes, int tamanho, int elementos) {
        return new CombinacaoRequest(getPosicoes(posicoes, tamanho), getElementos(elementos));
    }

    private static List<PosicaoDTO> getPosicoes(int posicoes, int tamanho) {
        List<PosicaoDTO> list = new ArrayList();
        for(int i=0; i<posicoes; i++) {
            list.add(new PosicaoDTO("P-"+i, tamanho));
        }
        return list;
    }

    private static List<ElementoDTO> getElementos(int elementos) {
        List<ElementoDTO> list = new ArrayList();
        for(int i=0; i<elementos; i++) {
            list.add(new ElementoDTO("E-"+i));
        }
        return list;
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
