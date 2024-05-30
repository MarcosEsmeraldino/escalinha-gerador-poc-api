package com.escalinha.escalinhageradorpocapi.combinator;

import lombok.experimental.UtilityClass;

import java.math.BigInteger;
import java.util.List;

@UtilityClass
public class MathHelper {

    public static BigInteger combinacoes (int n, int p) {
        if (p > n) {
            throw new RuntimeException("p nao pode ser maior que n");
        }
        return fatorial(n).divide(fatorial(p).multiply(fatorial(n-p)));
    }

    public static BigInteger fatorial (int n) {
        BigInteger result = BigInteger.ONE;
        for (int i=2; i<=n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    public static Double calcularDesvioPadrao(List<Integer> linha) {
        int n = linha.size();
        double media = calcularMedia(linha);

        // Calcular a soma dos quadrados das diferenças da média
        double soma = 0;
        for (int i = 0; i < n; i++) {
            soma += Math.pow(linha.get(i) - media, 2);
        }

        // Calcular o desvio padrão
        double variancia = soma / n;
        return Math.sqrt(variancia);
    }

    public static double calcularMedia(List<Integer> linha) {
        int soma = 0;
        for (int i = 0; i < linha.size(); i++) {
            soma += linha.get(i);
        }
        return (double) soma / linha.size();
    }
}
