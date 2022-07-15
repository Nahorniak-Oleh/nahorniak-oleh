package org.epam.nahorniak.spring.internetserviceprovider.exception;

import org.epam.nahorniak.spring.internetserviceprovider.model.enums.ErrorType;

public class SuchUserAlreadyExistException extends ServiceException{

    private static final String DEFAULT_MESSAGE = "Such user already exist";

    public SuchUserAlreadyExistException(String message) {
        super(message);
    }

    public SuchUserAlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DATABASE_ERROR_TYPE;
    }
}
