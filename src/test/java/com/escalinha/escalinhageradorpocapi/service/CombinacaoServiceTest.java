package com.escalinha.escalinhageradorpocapi.service;

import com.escalinha.escalinhageradorpocapi.dto.ElementoDTO;
import com.escalinha.escalinhageradorpocapi.utils.CombinacaoRequestUtils;
import com.escalinha.escalinhageradorpocapi.utils.CombinacaoResponseUtils;
import com.escalinha.escalinhageradorpocapi.utils.ElementoUtils;
import com.escalinha.escalinhageradorpocapi.validator.CombinacaoValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsSecondArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CombinacaoServiceTest {

    @Spy
    @InjectMocks
    private CombinacaoService service;

    @Mock
    private CombinacaoValidator combinacaoValidator;

    @ParameterizedTest
    @CsvSource({"27,3,2,3", "63504,2,5,10", "2000376,3,5,9"})
    public void retornaTodasPossibilidadesAoCombinar(int result, int posicoes, int tamanho, int elementos) {

        // não aplica filtro
        doAnswer(returnsSecondArg())
                .when(service)
                .filtrarEquilibradas(any(), any());

        var request = CombinacaoRequestUtils.getRequest(posicoes, tamanho, elementos);


        var response = service.combinar(request);


        assertNotNull(response);
        assertEquals(result, response.size());
    }

    // TODO: implementar varias tentativas
    @ParameterizedTest
    @CsvSource({"1,1"/*, "63504,2,5,10", "2000376,3,5,9"*/})
    public void retornaEquilibradasAoFiltrar(int equilibrados, int naoEquilibrados) {

        var elementos = ElementoUtils.getElementos(equilibrados + 1);
        var responses = CombinacaoResponseUtils.getResponses(elementos, equilibrados, naoEquilibrados);

        var response = service.filtrarEquilibradas(elementos, responses);

        assertNotNull(response);
        assertEquals(equilibrados, response.size());
//        responses.forEach(r -> {
//            assertEquals(0D, r.getScore());
//        });
    }
}