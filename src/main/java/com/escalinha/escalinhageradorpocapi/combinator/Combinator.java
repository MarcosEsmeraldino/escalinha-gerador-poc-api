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

    public static <T> long countCombine(int elementos, int tamanho) {
        if (tamanho > elementos) {
            return 0;
        }
        return CombinatoricsUtils.factorial(elementos) / (CombinatoricsUtils.factorial(tamanho) * CombinatoricsUtils.factorial(elementos - tamanho));
    }

    /**
     * Combinação de elementos com repetição usando abordagem iterativa com suporte a paginação.
     */
    public static <T> List<List<T>> combineRepeatablePaginated(List<T> elementos, int tamanho, int offset, int limit) {
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

    /**
     * Contagem de combinações do método combineRepeatablePaginated
     */
    public static <T> int countCombineRepeatable(List<T> elementos, int tamanho) {
        return (int) Math.pow(elementos.size(), tamanho);
    }
}
