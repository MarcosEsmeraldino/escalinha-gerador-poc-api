package com.escalinha.escalinhageradorpocapi.combinator;

import lombok.experimental.UtilityClass;
import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class Combinator {

    /**
     * Combinação de elementos sem repetição.
     */
    public static <T> List<List<T>> combine(List<T> elementos, int tamanho) {
        List<List<T>> combinations = new ArrayList<>();
        Iterator<int[]> iterator = CombinatoricsUtils.combinationsIterator(elementos.size(), tamanho);
        iterator.forEachRemaining(er -> {
            combinations.add(
                    Arrays.stream(er)
                            .boxed()
                            .map(i -> elementos.get(i))
                            .collect(Collectors.toList())
            );
        });

        return combinations;
    }

    /**
     * Combinação de elementos com repetição
     */
    public static <T> List<List<T>> combineRepeatable(List<T> elementos, int tamanho) {
        List<List<T>> result = new ArrayList<>();
        gerarCombinacoesRecursivo(elementos, tamanho, new ArrayList<>(), result);
        return result;
    }

    private static <T> void gerarCombinacoesRecursivo(List<T>  elementos, int tamanho, List<T> combinacaoAtual, List<List<T>> result) {
        if (combinacaoAtual.size() == tamanho) {
            result.add(new ArrayList<>(combinacaoAtual));
            return;
        }

        for (T elemento : elementos) {
            combinacaoAtual.add(elemento);
            gerarCombinacoesRecursivo(elementos, tamanho, combinacaoAtual, result);
            combinacaoAtual.remove(combinacaoAtual.size() - 1);
        }
    }
}
