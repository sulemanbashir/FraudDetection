package com.frauddetection.exception;

public class EntityPreprocessingException extends Exception {

    public EntityPreprocessingException(String message) {
        super(message);
    }

    public EntityPreprocessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
