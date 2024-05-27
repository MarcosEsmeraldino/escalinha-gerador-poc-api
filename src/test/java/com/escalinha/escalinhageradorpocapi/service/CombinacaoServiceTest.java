package com.escalinha.escalinhageradorpocapi.service;

import com.escalinha.escalinhageradorpocapi.dto.ElementoDTO;
import com.escalinha.escalinhageradorpocapi.dto.CombinacaoRequest;
import com.escalinha.escalinhageradorpocapi.dto.PosicaoDTO;
import com.escalinha.escalinhageradorpocapi.validator.CombinacaoValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class CombinacaoServiceTest {

    @InjectMocks
    private CombinacaoService service;

    @Mock
    private CombinacaoValidator combinacaoValidator;

    @Test
    public void retornaSucessoAoCombinar() {

        doReturn(true)
                .when(combinacaoValidator)
                .isValid(any());

        var response = service.combinar(getRequest());

        assertNotNull(response);
        assertEquals(1, response.size());
    }

    private CombinacaoRequest getRequest() {
        return new CombinacaoRequest(getPosicoes(), getElementos());
    }

    private List<PosicaoDTO> getPosicoes() {
        var g1 = new PosicaoDTO("POSICAO-1", 2);
        var g2 = new PosicaoDTO("POSICAO-2", 2);
        var g3 = new PosicaoDTO("POSICAO-3", 2);
        return List.of(g1, g2, g3);
    }

    private List<ElementoDTO> getElementos() {
        var c1 = new ElementoDTO("ELEMENTO-1");
        var c2 = new ElementoDTO("ELEMENTO-2");
        var c3 = new ElementoDTO("ELEMENTO-3");
        return List.of(c1, c2, c3);
    }
}