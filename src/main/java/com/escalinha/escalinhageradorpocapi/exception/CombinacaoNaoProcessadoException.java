package com.escalinha.escalinhageradorpocapi.exception;

public class CombinacaoNaoProcessadoException extends CombinacaoException {

    public CombinacaoNaoProcessadoException() {
    }

    public CombinacaoNaoProcessadoException(String message) {
        super(message);
    }

    public CombinacaoNaoProcessadoException(String message, Object... args) {
        super(message, args);
    }

    public CombinacaoNaoProcessadoException(String message, Throwable cause) {
        super(message, cause);
    }
}
