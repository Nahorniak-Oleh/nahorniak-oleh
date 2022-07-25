package org.epam.nahorniak.spring.internetserviceprovider.exception;

import org.epam.nahorniak.spring.internetserviceprovider.model.enums.ErrorType;

public class ServiceNotFoundException extends ServiceException{

    private static final String DEFAULT_MESSAGE = "Service is not found!";

    public ServiceNotFoundException(String message) {
        super(message);
    }

    public ServiceNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DATABASE_ERROR_TYPE;
    }
}
