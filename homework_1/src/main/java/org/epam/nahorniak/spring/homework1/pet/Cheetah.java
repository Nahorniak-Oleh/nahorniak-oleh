package org.epam.nahorniak.spring.homework1.pet;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(Ordered.LOWEST_PRECEDENCE)
@Component
public class Cheetah implements Animal{
    @Override
    public String getPet() {
        return "Cheetah";
    }
}
