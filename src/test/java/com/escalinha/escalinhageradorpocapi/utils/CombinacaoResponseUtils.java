package com.escalinha.escalinhageradorpocapi.utils;

import com.escalinha.escalinhageradorpocapi.dto.CombinacaoResponse;
import com.escalinha.escalinhageradorpocapi.dto.ElementoDTO;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CombinacaoResponseUtils {

    public static List<CombinacaoResponse> getResponses(List<ElementoDTO> elementos, int equilibrados, int naoEquilibrados) {
        var equilibradosList = getResponsesEquilibrados(elementos, equilibrados);
        var naoEquilibradosList = getResponsesNaoEquilibrados(elementos, naoEquilibrados);

        var responses = new ArrayList<CombinacaoResponse>();
        if(!equilibradosList.isEmpty()) {
            responses.addAll(equilibradosList);
        }
        if(!naoEquilibradosList.isEmpty()) {
            responses.addAll(naoEquilibradosList);
        }
        return responses;
    }

    public static List<CombinacaoResponse> getResponsesEquilibrados(List<ElementoDTO> elementos, int equilibrados) {
        var list = new ArrayList<CombinacaoResponse>();
        for(int i=0; i<equilibrados; i++) {
            list.add(getResponseEquilibrado(elementos, i));
        }
        return list;
    }

    public static CombinacaoResponse getResponseEquilibrado(List<ElementoDTO> elementos, int index) {
        return CombinacaoResponse.builder()
                .combinacao(List.of(
                        List.of(elementos.get(index))
                ))
                .build();
    }

    public static List<CombinacaoResponse> getResponsesNaoEquilibrados(List<ElementoDTO> elementos, int naoEquilibrados) {
        var list = new ArrayList<CombinacaoResponse>();
        for(int i=0; i<naoEquilibrados; i++) {
            list.add(getResponseNaoEquilibrado(elementos, i));
        }
        return list;
    }

    public static CombinacaoResponse getResponseNaoEquilibrado(List<ElementoDTO> elementos, int index) {
        return CombinacaoResponse.builder()
                .combinacao(List.of(
                        List.of(elementos.get(index), elementos.get(index))
                ))
                .build();
    }
}
