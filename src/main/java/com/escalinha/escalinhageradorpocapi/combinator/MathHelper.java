package com.escalinha.escalinhageradorpocapi.combinator;

import lombok.experimental.UtilityClass;

import java.math.BigInteger;

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
}
