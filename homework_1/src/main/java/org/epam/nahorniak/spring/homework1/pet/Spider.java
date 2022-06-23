package org.epam.nahorniak.spring.homework1.pet;

import org.springframework.stereotype.Component;

@Component
public class Spider implements Animal{
    @Override
    public String getPet() {
        return "Spider";
    }
}
