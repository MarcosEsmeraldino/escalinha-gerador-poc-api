package com.escalinha.escalinhageradorpocapi.utils;

import com.escalinha.escalinhageradorpocapi.dto.PosicaoDTO;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class PosicaoUtils {

    public static List<PosicaoDTO> getPosicoes(int posicoes, int tamanho) {
        List<PosicaoDTO> list = new ArrayList();
        for(int i=0; i<posicoes; i++) {
            list.add(new PosicaoDTO("P-"+i, tamanho));
        }
        return list;
    }
}
