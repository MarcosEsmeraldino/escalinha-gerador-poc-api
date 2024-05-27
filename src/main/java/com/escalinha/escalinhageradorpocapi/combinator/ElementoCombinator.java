package com.escalinha.escalinhageradorpocapi.combinator;

import com.escalinha.escalinhageradorpocapi.dto.ElementoDTO;

import java.util.List;

public class ElementoCombinator implements ICombinator<ElementoDTO>{

    @Override
    public List<List<ElementoDTO>> combine(List<ElementoDTO> group, int subGroupSize) {
        return ICombinator.super.combine(group, subGroupSize);
    }
}
