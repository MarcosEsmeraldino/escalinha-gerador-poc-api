package com.escalinha.escalinhageradorpocapi.service;

import com.escalinha.escalinhageradorpocapi.combinator.Combinator;
import com.escalinha.escalinhageradorpocapi.combinator.MathHelper;
import com.escalinha.escalinhageradorpocapi.dto.CombinacaoRequest;
import com.escalinha.escalinhageradorpocapi.dto.CombinacaoResponse;
import com.escalinha.escalinhageradorpocapi.dto.ElementoDTO;
import com.escalinha.escalinhageradorpocapi.validator.CombinacaoValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CombinacaoService {

    @Autowired
    private CombinacaoValidator combinacaoValidator;

    public List<CombinacaoResponse> combinar(CombinacaoRequest request) {
        combinacaoValidator.isValid(request);
        var pageRequest = PageRequest.of(0, 1000000);
        return combinar(request, pageRequest);
    }

    private List<CombinacaoResponse> combinar(CombinacaoRequest request, PageRequest pageRequest) {
        var elementos = request.elementos();
        var tamanhoPosicao = request.posicoes().get(0).tamanho();
        var elementosCombinados = Combinator.combine(elementos, tamanhoPosicao);

        List<CombinacaoResponse> listaCombinacoes = new ArrayList<>();
        while (true) {

            var combinacoesPaginadas = gerarCombinacoesPaginado(request, elementosCombinados, pageRequest);

            var combinacoesFiltradas = filtrarEquilibradas(elementos, combinacoesPaginadas.getContent());

            if(!combinacoesFiltradas.isEmpty()) {
                listaCombinacoes.addAll(combinacoesFiltradas);
            }


            if (combinacoesPaginadas.isLast()) {
                break;
            }

            pageRequest = pageRequest.next();
        }

        return listaCombinacoes;
    }

    private Page<CombinacaoResponse> gerarCombinacoesPaginado(CombinacaoRequest request, List<List<ElementoDTO>> elementosCombinados, Pageable pageable) {
        var quantidadePosicoes = request.posicoes().size();

        // Calcule o número total de combinações possíveis
        int totalCombinations = Combinator.countCombineRepeatable(elementosCombinados, quantidadePosicoes);

        var pageSize = (pageable.getOffset() + pageable.getPageSize()) > totalCombinations
                ? (int) Math.abs(totalCombinations - pageable.getOffset())
                : pageable.getPageSize();

        var gruposCombinados = Combinator.combineRepeatablePaginated(elementosCombinados, quantidadePosicoes, pageable.getPageNumber(), pageSize);

        var gruposCombinadosList = gruposCombinados.stream()
                .map(gc -> CombinacaoResponse.builder()
                        .combinacao(gc)
                        .score(0d)
                        .build()
                )
                .toList();

        return new PageImpl<CombinacaoResponse>(gruposCombinadosList, pageable, totalCombinations);
    }

    public List<CombinacaoResponse> filtrarEquilibradas(List<ElementoDTO> elementos, List<CombinacaoResponse> responses) {
        // calcula score de cada combinação
        responses.forEach(r -> calcularScore(r, elementos));

        // filtra combinações que possuem o melhor score
        return filtrarCombinacoes(responses);
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
