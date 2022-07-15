package org.epam.nahorniak.spring.internetserviceprovider.exception;

import org.epam.nahorniak.spring.internetserviceprovider.model.enums.ErrorType;

public class UserNotFoundException extends ServiceException{

    private static final String DEFAULT_MESSAGE = "User is not found!";

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DATABASE_ERROR_TYPE;
    }
}
