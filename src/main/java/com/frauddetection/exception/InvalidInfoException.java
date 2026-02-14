package com.frauddetection.exception;

public class InvalidInfoException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidInfoException() {
    }

    public InvalidInfoException(String message) {
        super(message);
    }

    public InvalidInfoException(Throwable cause) {
        super(cause);
    }

    public InvalidInfoException(String message, Throwable cause) {
        super(message, cause);
    }

}
