package org.epam.nahorniak.spring.internetserviceprovider.exception;

import org.epam.nahorniak.spring.internetserviceprovider.model.enums.ErrorType;

public class TariffNotFoundException extends ServiceException{

    private static final String DEFAULT_MESSAGE = "Tariff is not found!";

    public TariffNotFoundException(String message) {
        super(message);
    }

    public TariffNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DATABASE_ERROR_TYPE;
    }

}
