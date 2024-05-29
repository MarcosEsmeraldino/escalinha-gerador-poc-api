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
        assertEquals(27, response.size());
    }

    private CombinacaoRequest getRequest() {
        return new CombinacaoRequest(getPosicoes(), getElementos());
    }

    private List<PosicaoDTO> getPosicoes() {
        return List.of(
                new PosicaoDTO("P-1", 2),
                new PosicaoDTO("P-2", 2),
                new PosicaoDTO("P-3", 2)
        );
    }

    private List<ElementoDTO> getElementos() {
        return List.of(
                new ElementoDTO("E-1"),
                new ElementoDTO("E-2"),
                new ElementoDTO("E-3")
        );
    }
}