package com.escalinha.escalinhageradorpocapi.service;

import com.escalinha.escalinhageradorpocapi.dto.CandidatoDTO;
import com.escalinha.escalinhageradorpocapi.dto.EscalaDTO;
import com.escalinha.escalinhageradorpocapi.dto.GrupoDTO;
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
        var response = service.processar(getEscalaDTO(), null);

        assertNotNull(response);
        assertEquals(10, response.size());
    }

    @Test
    public void retornaListaLimitadaAoProcessarEscala() {
        var limite = 1;

        var response = service.processar(getEscalaDTO(), limite);

        assertNotNull(response);
        assertEquals(limite, response.size());
    }

    @Test
    public void retornaErro422AoProcessarEscalaNaoProcessavel() {
        var escalaNaoProcessavel = getEscalaNaoProcessavel();

        var response = assertThrows(NaoProcessadoException.class,
                () -> service.processar(escalaNaoProcessavel, null));

        assertNotNull(response);
    }

    private EscalaDTO getEscalaDTO() {
        return new EscalaDTO("ESCALA-1", getGrupos(), getCandidatos());
    }

    private EscalaDTO getEscalaNaoProcessavel() {
        return getEscalaDTO();
    }

    private List<GrupoDTO> getGrupos() {
        var g1 = new GrupoDTO("GRUPO-1", 2);
        var g2 = new GrupoDTO("GRUPO-2", 2);
        var g3 = new GrupoDTO("GRUPO-3", 2);
        return List.of(g1, g2, g3);
    }

    private List<CandidatoDTO> getCandidatos() {
        var c1 = new CandidatoDTO("CANDIDATO-1");
        var c2 = new CandidatoDTO("CANDIDATO-2");
        var c3 = new CandidatoDTO("CANDIDATO-3");
        return List.of(c1, c2, c3);
    }
}