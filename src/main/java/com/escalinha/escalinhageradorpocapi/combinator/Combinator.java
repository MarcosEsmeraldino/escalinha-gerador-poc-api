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

    /*@SuppressWarnings("unchecked")
    public static <T> T[][] combineRepeatable(T[] elementos, int tamanho) {
        if (tamanho <= 0) {
            // Retorna um array vazio de arrays vazios
            return (T[][]) Array.newInstance(elementos.getClass(), 1);
        }

        // List to hold the results
        List<T[]> resultList = new ArrayList<>();

        // Temporary array to hold each combination
        T[] combinacaoAtual = (T[]) Array.newInstance(elementos.getClass().getComponentType(), tamanho);

        // Generate combinations
        gerarCombinacoesRecursivo(elementos, tamanho, 0, combinacaoAtual, resultList);

        // Convert list to array
        T[][] resultArray = (T[][]) Array.newInstance(elementos.getClass().getComponentType(), resultList.size(), tamanho);
        for (int i = 0; i < resultList.size(); i++) {
            resultArray[i] = resultList.get(i);
        }

        return resultArray;
    }

    private static <T> void gerarCombinacoesRecursivo(T[] elementos, int tamanho, int pos, T[] combinacaoAtual, List<T[]> result) {
        if (pos == tamanho) {
            result.add(combinacaoAtual.clone());
            return;
        }
        for (T elemento : elementos) {
            combinacaoAtual[pos] = elemento;
            gerarCombinacoesRecursivo(elementos, tamanho, pos + 1, combinacaoAtual, result);
        }
    }*/
}
