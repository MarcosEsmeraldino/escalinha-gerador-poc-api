package com.escalinha.escalinhageradorpocapi.service;

import com.escalinha.escalinhageradorpocapi.combinator.ElementoCombinator;
import com.escalinha.escalinhageradorpocapi.combinator.GrupoCombinator;
import com.escalinha.escalinhageradorpocapi.dto.CombinacaoRequest;
import com.escalinha.escalinhageradorpocapi.dto.CombinacaoResponse;
import com.escalinha.escalinhageradorpocapi.dto.ElementoDTO;
import com.escalinha.escalinhageradorpocapi.validator.CombinacaoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CombinacaoService {

    @Autowired
    private CombinacaoValidator combinacaoValidator;

    public List<CombinacaoResponse> combinar(CombinacaoRequest request) {
        combinacaoValidator.isValid(request);

        var todasCombinacoes = gerarTodasCombinacoes(request);
        var combinacoesEquilibradas = filtrarCombinacoesDesequilibradas(todasCombinacoes);

        return combinacoesEquilibradas;
    }

    private List<CombinacaoResponse> gerarTodasCombinacoes(CombinacaoRequest request) {
        var elementos = request.elementos();
        var tamanhoPosicao = request.posicoes().get(0).tamanho();
        var elementosCombinados = combinarElementos(elementos, tamanhoPosicao);

        var quantidadePosicoes = request.posicoes().size();
        var gruposCombinados = combinarGrupos(elementosCombinados, quantidadePosicoes);

        return gruposCombinados.stream()
                .map(gc -> new CombinacaoResponse(gc, 0))
                .toList();
    }

    private List<List<ElementoDTO>> combinarElementos(List<ElementoDTO> grupo, Integer subGrupoSize) {
        return new ElementoCombinator().combine(grupo, subGrupoSize);
    }

    private List<List<List<ElementoDTO>>> combinarGrupos(List<List<ElementoDTO>> grupos, Integer quantidade) {
        return new GrupoCombinator().combine(grupos, quantidade);
    }

    private List<CombinacaoResponse> filtrarCombinacoesDesequilibradas(List<CombinacaoResponse> responses) {
        // TODO: implementar filtro
        return responses;
    }
}
