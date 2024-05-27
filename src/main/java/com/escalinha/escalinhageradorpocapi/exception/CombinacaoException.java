package com.escalinha.escalinhageradorpocapi.exception;

public class CombinacaoException extends RuntimeException {

    public CombinacaoException() {
    }

    public CombinacaoException(String message) {
        super(message);
    }

    public CombinacaoException(String message, Object... args) {
        super(String.format(message, args));
    }

    public CombinacaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
