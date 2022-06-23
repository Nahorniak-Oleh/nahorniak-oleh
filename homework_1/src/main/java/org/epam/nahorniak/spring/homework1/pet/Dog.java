package org.epam.nahorniak.spring.homework1.pet;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class Dog implements Animal{
    @Override
    public String getPet() {
        return "Dog";
    }
}
