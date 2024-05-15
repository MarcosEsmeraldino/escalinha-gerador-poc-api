package com.escalinha.escalinhageradorpocapi.exception;

public class EscalinhaException extends RuntimeException {

    public EscalinhaException() {
    }

    public EscalinhaException(String message) {
        super(message);
    }

    public EscalinhaException(String message, Object... args) {
        super(String.format(message, args));
    }

    public EscalinhaException(String message, Throwable cause) {
        super(message, cause);
    }
}
