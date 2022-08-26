package org.epam.nahorniak.spring.internetserviceprovider.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.epam.nahorniak.spring.internetserviceprovider.model.enums.ErrorType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Error {

    private String message;
    private ErrorType errorType;
    private LocalDateTime timeStamp;

}
