package com.escalinha.escalinhageradorpocapi.combinator;

import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public interface ICombinator<T> {

    default List<List<T>> combine(List<T> group, int subGroupSize) {
        List<List<T>> combinations = new ArrayList<>();
        Iterator<int[]> iterator = CombinatoricsUtils.combinationsIterator(group.size(), subGroupSize);
        iterator.forEachRemaining(er -> {
            combinations.add(
                    Arrays.stream(er)
                            .boxed()
                            .map(i -> group.get(i))
                            .collect(Collectors.toList())
            );
        });

        return combinations;
    }
}
