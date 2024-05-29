package com.escalinha.escalinhageradorpocapi.service;

import com.escalinha.escalinhageradorpocapi.combinator.Combinator;
import com.escalinha.escalinhageradorpocapi.dto.CombinacaoRequest;
import com.escalinha.escalinhageradorpocapi.dto.CombinacaoResponse;
import com.escalinha.escalinhageradorpocapi.dto.ElementoDTO;
import com.escalinha.escalinhageradorpocapi.validator.CombinacaoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CombinacaoService {

    @Autowired
    private CombinacaoValidator combinacaoValidator;

    public List<CombinacaoResponse> combinar(CombinacaoRequest request) {
        combinacaoValidator.isValid(request);

        var todasCombinacoes = gerarTodasCombinacoes(request);
        var combinacoesEquilibradas = filtrarCombinacoesDesequilibradas(request, todasCombinacoes);

        return combinacoesEquilibradas;
    }

    private List<CombinacaoResponse> gerarTodasCombinacoes(CombinacaoRequest request) {
        var elementos = request.elementos();
        var tamanhoPosicao = request.posicoes().get(0).tamanho();
        var elementosCombinados = combinarElementos(elementos, tamanhoPosicao);

        var quantidadePosicoes = request.posicoes().size();
        var gruposCombinados = combinarGrupos(elementosCombinados, quantidadePosicoes);

        return gruposCombinados.stream()
                .map(gc -> {
                    var cr = new CombinacaoResponse();
                    cr.setCombinacao(gc);
                    return cr;
                })
                .toList();
    }

    private List<List<ElementoDTO>> combinarElementos(List<ElementoDTO> grupo, Integer subGrupoSize) {
        // elementos não podem repetir dentro de um grupo
        return Combinator.combine(grupo, subGrupoSize);
    }

    private List<List<List<ElementoDTO>>> combinarGrupos(List<List<ElementoDTO>> grupos, Integer quantidade) {
        // grupos podem repetir dentro de uma combinação
        return Combinator.combineRepeatable(grupos, quantidade);
    }

    private List<CombinacaoResponse> filtrarCombinacoesDesequilibradas(CombinacaoRequest request, List<CombinacaoResponse> responses) {
        var fatorEquilibrio = calcularFatorEquilibrio(request);
        var elementos = request.elementos();

        // calcula fator de equilibrio de cada combinação
        responses.forEach(r -> calculaScore(r, elementos, fatorEquilibrio));

        // filtra combinações que possuem o melhor score
        return filtrarCombinacoes(responses);
    }

    private double calcularFatorEquilibrio(CombinacaoRequest request) {
        var posicoes = request.posicoes().size();
        var tamanhoPosicao = request.posicoes().get(0).tamanho();
        var posicoesTotal = posicoes * tamanhoPosicao;
        return (double) posicoesTotal / request.elementos().size();
    }

    private void calculaScore(CombinacaoResponse combinacao, List<ElementoDTO> elementos, double fatorEquilibrio) {
        // calcula quantas ocorrencias cada elemento possui
        Map<String, Integer> elementoOcorrencias = elementos.stream().collect(Collectors.toMap(ElementoDTO::id, p -> 0));
        elementos.forEach(e -> {
            combinacao.combinacao().forEach(c -> {
                c.forEach(e1 -> {
                    if(e1.id().equals(e.id())) {
                        elementoOcorrencias.put(e.id(), elementoOcorrencias.get(e.id())+1);
                    }
                });
            });
        });

        AtomicReference<Double> scoreAtomic = new AtomicReference<>(0d);
        elementoOcorrencias.forEach((k,v) -> {
            double pessoaScore = Math.abs(v - fatorEquilibrio);
            scoreAtomic.updateAndGet(a -> a + pessoaScore);
        });

        Double score = scoreAtomic.get();
        combinacao.setScore(score);
    }

    private List<CombinacaoResponse> filtrarCombinacoes(List<CombinacaoResponse> responses) {
        var menorScore = getMenorScore(responses);

        return responses.stream()
                .filter(r -> r.getScore() <= menorScore)
                .toList();
    }

    private Double getMenorScore(List<CombinacaoResponse> responses) {
        return responses.stream()
                .mapToDouble(CombinacaoResponse::getScore)
                .min()
                .orElse(Double.MAX_VALUE);
    }
}
