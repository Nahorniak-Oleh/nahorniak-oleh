package org.epam.nahorniak.spring.internetserviceprovider.exceptions;

import org.epam.nahorniak.spring.internetserviceprovider.model.ErrorType;

public class EntityNotFoundException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "Entity is not found";

    public EntityNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public ErrorType getErrorType() {
        return ErrorType.NOT_FOUND_ERROR_TYPE;
    }
}
