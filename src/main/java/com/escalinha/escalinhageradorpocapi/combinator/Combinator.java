package com.escalinha.escalinhageradorpocapi.combinator;

import lombok.experimental.UtilityClass;
import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.*;
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

    /**
     * Combinação de elementos com repetição usando abordagem iterativa
     * com suporte a paginação.
     */
    public static <T> List<List<T>> combineRepeatable(List<T> elementos, int tamanho, int offset, int limit) {
        List<List<T>> result = new ArrayList<>();
        int[] indices = new int[tamanho];
        Arrays.fill(indices, 0);

        int count = 0;
        while (indices[0] < elementos.size()) {
            if (count >= offset && result.size() < limit) {
                List<T> combination = new ArrayList<>();
                for (int index : indices) {
                    combination.add(elementos.get(index));
                }
                result.add(combination);
            }

            // Increment the indices
            int i = tamanho - 1;
            while (i >= 0 && indices[i] == elementos.size() - 1) {
                i--;
            }
            if (i < 0) break;

            indices[i]++;
            for (int j = i + 1; j < tamanho; j++) {
                indices[j] = 0;
            }

            count++;
        }

        return result;
    }
}
