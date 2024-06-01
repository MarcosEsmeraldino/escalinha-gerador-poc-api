package com.escalinha.escalinhageradorpocapi.service;

import com.escalinha.escalinhageradorpocapi.combinator.Combinator;
import com.escalinha.escalinhageradorpocapi.combinator.MathHelper;
import com.escalinha.escalinhageradorpocapi.dto.CombinacaoRequest;
import com.escalinha.escalinhageradorpocapi.dto.CombinacaoResponse;
import com.escalinha.escalinhageradorpocapi.dto.ElementoDTO;
import com.escalinha.escalinhageradorpocapi.validator.CombinacaoValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
@Service
public class CombinacaoService {

    public List<CombinacaoResponse> combinar(CombinacaoRequest request) {
        CombinacaoValidator.isValid(request);

//        var todasCombinacoes = gerarTodasCombinacoes(request);
//        var combinacoesEquilibradas = filtrarEquilibradas(request, todasCombinacoes);
//
//        return combinacoesEquilibradas;

        var elementos = request.elementos();
        var tamanhoPosicao = request.posicoes().get(0).tamanho();
        var elementosCombinados = combinarElementos(elementos, tamanhoPosicao);

        List<CombinacaoResponse> combinacoesEquilibradas = null;
        int page=0;
        int size=100000;
        do {
            var todasCombinacoes = gerarTodasCombinacoesPaginado(request, elementosCombinados, page, size);
            combinacoesEquilibradas = filtrarEquilibradas(request, todasCombinacoes);
            page++;
        } while(isNull(combinacoesEquilibradas));

        return combinacoesEquilibradas;
    }

    private List<CombinacaoResponse> gerarTodasCombinacoes(CombinacaoRequest request) {
        var elementos = request.elementos();
        var tamanhoPosicao = request.posicoes().get(0).tamanho();

        var elementosCombinados = combinarElementos(elementos, tamanhoPosicao);

        var quantidadePosicoes = request.posicoes().size();
        var gruposCombinados = combinarGrupos(elementosCombinados, quantidadePosicoes);

        return gruposCombinados.stream()
                .map(gc -> CombinacaoResponse.builder()
                            .combinacao(gc)
                            .score(0d)
                            .build()
                )
                .toList();
    }

    private List<CombinacaoResponse> gerarTodasCombinacoesPaginado(CombinacaoRequest request, List<List<ElementoDTO>> elementosCombinados, int page, int size) {
        log.info("page: {}, size: {}", page, size);
        var quantidadePosicoes = request.posicoes().size();
        var gruposCombinados = combinarGruposPaginado(elementosCombinados, quantidadePosicoes, page, size);

        return gruposCombinados.stream()
                .map(gc -> CombinacaoResponse.builder()
                            .combinacao(gc)
                            .score(0d)
                            .build()
                )
                .toList();
    }

    /**
     * Elementos não podem repetir dentro de um grupo
     */
    private List<List<ElementoDTO>> combinarElementos(List<ElementoDTO> grupo, Integer subGrupoSize) {
        return Combinator.combine(grupo, subGrupoSize);
    }

    /**
     * Grupos podem repetir dentro de uma combinação
     */
    private List<List<List<ElementoDTO>>> combinarGrupos(List<List<ElementoDTO>> grupos, Integer quantidade) {
        return Combinator.combineRepeatable(grupos, quantidade);
    }

    private List<List<List<ElementoDTO>>> combinarGruposPaginado(List<List<ElementoDTO>> grupos, Integer quantidade, int page, int size) {
        return Combinator.combineRepeatable(grupos, quantidade, page, size);
    }

    private List<CombinacaoResponse> filtrarEquilibradas(CombinacaoRequest request, List<CombinacaoResponse> responses) {
        var elementos = request.elementos();

        // calcula score de cada combinação
        responses.forEach(r -> calcularScore(r, elementos));

        // filtra combinações que possuem o melhor score
        return filtrarCombinacoes(responses);
    }

    private double calcularFatorEquilibrio(CombinacaoRequest request) {
        var posicoes = request.posicoes().size();
        var tamanhoPosicao = request.posicoes().get(0).tamanho();
        var posicoesTotal = posicoes * tamanhoPosicao;
        return (double) posicoesTotal / request.elementos().size();
    }

    private void calcularScore(CombinacaoResponse combinacao, List<ElementoDTO> elementos) {
        var elementoOcorrencias = calcularOcorrencias(combinacao, elementos);
        var score = MathHelper.calcularDesvioPadrao(elementoOcorrencias);
        combinacao.setScore(score);
    }

    private List<Integer> calcularOcorrencias(CombinacaoResponse combinacao, List<ElementoDTO> elementos) {
        return elementos.stream()
                .map(e -> calcularOcorrencias(combinacao, e))
                .toList();
    }

    private Integer calcularOcorrencias(CombinacaoResponse combinacao, ElementoDTO elemento) {
        return combinacao.getCombinacao().parallelStream()
                .flatMap(List::stream)
                .map(ElementoDTO::id)
                .filter(id -> id.equals(elemento.id()))
                .collect(Collectors.counting())
                .intValue();
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
