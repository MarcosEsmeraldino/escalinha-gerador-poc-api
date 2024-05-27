package com.escalinha.escalinhageradorpocapi.service;

import com.escalinha.escalinhageradorpocapi.dto.ElementoDTO;
import com.escalinha.escalinhageradorpocapi.dto.CombinacaoRequest;
import com.escalinha.escalinhageradorpocapi.dto.PosicaoDTO;
import com.escalinha.escalinhageradorpocapi.exception.NaoProcessadoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProcessamentoServiceTest {

    @InjectMocks
    private ProcessamentoService service;

    @Test
    public void retornaListaAoProcessarEscala() {
        var response = service.processar(getRequest());

        assertNotNull(response);
        assertEquals(10, response.size());
    }

    @Test
    public void retornaErro422AoProcessarEscalaNaoProcessavel() {
        var escalaNaoProcessavel = getEscalaNaoProcessavel();

        var response = assertThrows(NaoProcessadoException.class,
                () -> service.processar(escalaNaoProcessavel));

        assertNotNull(response);
    }

    private CombinacaoRequest getRequest() {
        return new CombinacaoRequest(getGrupos(), getCandidatos());
    }

    private CombinacaoRequest getEscalaNaoProcessavel() {
        return getRequest();
    }

    private List<PosicaoDTO> getGrupos() {
        var g1 = new PosicaoDTO("GRUPO-1", 2);
        var g2 = new PosicaoDTO("GRUPO-2", 2);
        var g3 = new PosicaoDTO("GRUPO-3", 2);
        return List.of(g1, g2, g3);
    }

    private List<ElementoDTO> getCandidatos() {
        var c1 = new ElementoDTO("CANDIDATO-1");
        var c2 = new ElementoDTO("CANDIDATO-2");
        var c3 = new ElementoDTO("CANDIDATO-3");
        return List.of(c1, c2, c3);
    }
}