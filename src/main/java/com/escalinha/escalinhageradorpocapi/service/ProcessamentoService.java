package com.escalinha.escalinhageradorpocapi.service;

import com.escalinha.escalinhageradorpocapi.dto.CombinacaoRequest;
import com.escalinha.escalinhageradorpocapi.dto.CombinacaoResponse;
import com.escalinha.escalinhageradorpocapi.exception.NaoProcessadoException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ProcessamentoService {

    private static final String MSG_NAO_FOI_POSSIVEL_PROCESSAR_ESCALA = "Não foi possível processar a escala";

    public List<CombinacaoResponse> processar(CombinacaoRequest escala) {
        List<CombinacaoResponse> lista = List.of(new CombinacaoResponse(null, null));

        if(CollectionUtils.isEmpty(lista)) {
            throw new NaoProcessadoException(MSG_NAO_FOI_POSSIVEL_PROCESSAR_ESCALA);
        }

        return lista;
    }
}
