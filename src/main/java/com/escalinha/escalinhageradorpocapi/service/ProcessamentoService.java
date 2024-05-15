package com.escalinha.escalinhageradorpocapi.service;

import com.escalinha.escalinhageradorpocapi.dto.EscalaDTO;
import com.escalinha.escalinhageradorpocapi.dto.EscalaPreenchidaDTO;
import com.escalinha.escalinhageradorpocapi.exception.NaoProcessadoException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
public class ProcessamentoService {

    private static final String MSG_NAO_FOI_POSSIVEL_PROCESSAR_ESCALA = "Não foi possível processar a escala {}";

    public List<EscalaPreenchidaDTO> processar(EscalaDTO escala, Integer limite) {
        var lista = processar(escala);

        if(CollectionUtils.isEmpty(lista)) {
            throw new NaoProcessadoException(MSG_NAO_FOI_POSSIVEL_PROCESSAR_ESCALA, escala.id());
        }

        if(Objects.nonNull(limite) && lista.size() > limite) {
            return lista.subList(0, limite);
        } else {
            return lista;
        }
    }

    private List<EscalaPreenchidaDTO> processar(EscalaDTO escala) {
        // TODO
        return List.of();
    }
}
