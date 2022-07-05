package org.epam.nahorniak.spring.homework1.pet;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class Cat implements Animal{
    @Override
    public String getPet() {
        return "Cat";
    }
}
