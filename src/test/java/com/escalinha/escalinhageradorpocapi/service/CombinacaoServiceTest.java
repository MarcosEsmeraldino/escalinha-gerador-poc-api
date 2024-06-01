package com.escalinha.escalinhageradorpocapi.service;

import com.escalinha.escalinhageradorpocapi.dto.ElementoDTO;
import com.escalinha.escalinhageradorpocapi.dto.CombinacaoRequest;
import com.escalinha.escalinhageradorpocapi.dto.PosicaoDTO;
import org.junit.jupiter.api.Test;
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

    @Test
    public void retornaSucessoAoCombinarTimeCozinha() {

        try {

            var response = service.combinar(getRequestCozinha());
            System.out.println(response.size());
//            response.forEach(System.out::println);

            assertNotNull(response);

        } catch (OutOfMemoryError error) {
            error.printStackTrace();
            assertNull(error);
        }
    }

    private CombinacaoRequest getRequestCozinha() {
        var posicoes = List.of(
                new PosicaoDTO("02/06", 3),
                new PosicaoDTO("09/06", 3),
                new PosicaoDTO("16/06", 3),
                new PosicaoDTO("23/06", 3)
        );

        var elementos = List.of(
                new ElementoDTO("Ana"),
                new ElementoDTO("Ariete"),
                new ElementoDTO("Karina"),
                new ElementoDTO("Priscila"),
                new ElementoDTO("Gabriele"),
                new ElementoDTO("Nicolle"),
                new ElementoDTO("Aline"),
                new ElementoDTO("Luana")
        );
        return new CombinacaoRequest(posicoes, elementos);
    }
}