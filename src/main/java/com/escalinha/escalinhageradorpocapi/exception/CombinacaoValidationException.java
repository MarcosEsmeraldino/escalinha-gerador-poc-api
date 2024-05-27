package com.escalinha.escalinhageradorpocapi.exception;

public class CombinacaoValidationException extends CombinacaoException {
    public CombinacaoValidationException() {
    }

    public CombinacaoValidationException(String message) {
        super(message);
    }

    public CombinacaoValidationException(String message, Object... args) {
        super(message, args);
    }

    public CombinacaoValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
