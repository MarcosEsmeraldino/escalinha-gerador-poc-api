package com.escalinha.escalinhageradorpocapi.utils;

import com.escalinha.escalinhageradorpocapi.dto.ElementoDTO;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ElementoUtils {

    public static List<ElementoDTO> getElementos(int elementos) {
        List<ElementoDTO> list = new ArrayList();
        for(int i=0; i<elementos; i++) {
            list.add(new ElementoDTO("E-"+i));
        }
        return list;
    }
}
