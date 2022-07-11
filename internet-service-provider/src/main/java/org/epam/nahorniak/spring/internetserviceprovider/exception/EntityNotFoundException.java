package org.epam.nahorniak.spring.internetserviceprovider.exception;

public class EntityNotFoundException extends RuntimeException{

    private static final String ENTITY_IS_NOT_FOUND = "Entity is not found";

    public EntityNotFoundException(){
        super(ENTITY_IS_NOT_FOUND);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
