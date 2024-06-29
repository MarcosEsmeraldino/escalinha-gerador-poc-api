package com.escalinha.escalinhageradorpocapi.combinator;

import com.escalinha.escalinhageradorpocapi.utils.CombinacaoRequestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CombinatorTest {

    @ParameterizedTest
    @CsvSource({"3,3,2,3", "252,2,5,10", "126,3,5,9"})
    public void retornaGruposAoCombinarElementos(int result, int posicoes, int tamanho, int elementos) {

        var request = CombinacaoRequestUtils.getRequest(posicoes, tamanho, elementos);

        var response = Combinator.combine(request.elementos(), tamanho);

        assertNotNull(response);
        assertEquals(result, response.size());
    }
}