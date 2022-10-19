package org.epam.nahorniak.spring.internetserviceprovider.exception;

import org.epam.nahorniak.spring.internetserviceprovider.model.enums.ErrorType;

public class RequestNotFoundException extends ServiceException{

    private static final String DEFAULT_MESSAGE = "Request is not found!";

    public RequestNotFoundException(String message) {
        super(message);
    }

    public RequestNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DATABASE_ERROR_TYPE;
    }
}
