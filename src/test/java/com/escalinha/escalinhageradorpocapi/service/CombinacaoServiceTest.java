package com.escalinha.escalinhageradorpocapi.service;

import com.escalinha.escalinhageradorpocapi.dto.ElementoDTO;
import com.escalinha.escalinhageradorpocapi.dto.CombinacaoRequest;
import com.escalinha.escalinhageradorpocapi.dto.PosicaoDTO;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CombinacaoServiceTest {

    @InjectMocks
    private CombinacaoService service;

    @ParameterizedTest
    @CsvSource({"6,3,2,3", "252,2,5,10"/*, "252,3,5,10"*/})
    public void retornaSucessoAoCombinar(int result, int posicoes, int tamanho, int elementos) {

        var response = service.combinar(getRequest(posicoes, tamanho, elementos));

        System.out.println(response.size());
        response.forEach(System.out::println);

        assertNotNull(response);
        assertEquals(result, response.size());
    }

    private CombinacaoRequest getRequest(int posicoes, int tamanho, int elementos) {
        return new CombinacaoRequest(getPosicoes(posicoes, tamanho), getElementos(elementos));
    }

    private List<PosicaoDTO> getPosicoes(int posicoes, int tamanho) {
        List<PosicaoDTO> list = new ArrayList();
        for(int i=0; i<posicoes; i++) {
            list.add(new PosicaoDTO("P-"+i, tamanho));
        }
        return list;
    }

    private List<ElementoDTO> getElementos(int elementos) {
        List<ElementoDTO> list = new ArrayList();
        for(int i=0; i<elementos; i++) {
            list.add(new ElementoDTO("E-"+i));
        }
        return list;
    }
}