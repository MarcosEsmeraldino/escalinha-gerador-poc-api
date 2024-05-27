package com.escalinha.escalinhageradorpocapi.combinator;

import com.escalinha.escalinhageradorpocapi.dto.ElementoDTO;

import java.util.List;

public class GrupoCombinator implements ICombinator<List<ElementoDTO>> {

    @Override
    public List<List<List<ElementoDTO>>> combine(List<List<ElementoDTO>> group, int subGroupSize) {
        return ICombinator.super.combine(group, subGroupSize);
    }
}
