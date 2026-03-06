package com.frauddetection.shared.exception;

public class EntityPreprocessingException extends Exception {

    public EntityPreprocessingException(String message) {
        super(message);
    }

    public EntityPreprocessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
