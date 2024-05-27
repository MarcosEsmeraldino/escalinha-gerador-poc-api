package com.escalinha.escalinhageradorpocapi.validator;

import com.escalinha.escalinhageradorpocapi.combinator.MathHelper;
import com.escalinha.escalinhageradorpocapi.dto.CombinacaoRequest;
import com.escalinha.escalinhageradorpocapi.dto.PosicaoDTO;
import com.escalinha.escalinhageradorpocapi.exception.CombinacaoValidationException;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@Component
public class CombinacaoValidator {

    private static final String MSG_POSICOES_POSSUEM_TAMANHO_DIFERENTE = "Todas as posições precisam ter o mesmo tamanho.";
    private static final String MSG_QUANTIDADE_COMBINACOES_GRANDE_DEMAIS = "A quantidade de combinações possíveis é grande demais";

    public boolean isValid(CombinacaoRequest request) {
        if(possuiTamanhoDiferente(request.posicoes())) {
            throw new CombinacaoValidationException(MSG_POSICOES_POSSUEM_TAMANHO_DIFERENTE);
        }

        if(quantidadeCombinacoesGrandeDemais(request)) {
            throw new CombinacaoValidationException(MSG_QUANTIDADE_COMBINACOES_GRANDE_DEMAIS);
        }

        return true;
    }

    private boolean possuiTamanhoDiferente(List<PosicaoDTO> posicoes) {
        Integer tamanho = null;

        for(PosicaoDTO p : posicoes) {
            if(Objects.isNull(tamanho)) {
                tamanho = p.tamanho();
            } else {
                if(tamanho != p.tamanho()) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean quantidadeCombinacoesGrandeDemais(CombinacaoRequest request) {
        try {
            BigInteger combinacoesBI = MathHelper.combinacoes(request.elementos().size(), request.posicoes().get(0).tamanho());
            int combinacoes = combinacoesBI.intValueExact();
            BigInteger todasAsCombinacoesBI = MathHelper.combinacoes(combinacoes, request.posicoes().size());
            todasAsCombinacoesBI.intValueExact();
        } catch (ArithmeticException ae) {
            return true;
        }
        return false;
    }
}
