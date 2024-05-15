package com.escalinha.escalinhageradorpocapi.exception;

public class NaoProcessadoException extends EscalinhaException {

    public NaoProcessadoException() {
    }

    public NaoProcessadoException(String message) {
        super(message);
    }

    public NaoProcessadoException(String message, Object... args) {
        super(message, args);
    }

    public NaoProcessadoException(String message, Throwable cause) {
        super(message, cause);
    }
}
