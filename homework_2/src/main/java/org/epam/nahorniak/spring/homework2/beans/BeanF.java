package org.epam.nahorniak.spring.homework2.beans;

import org.springframework.stereotype.Component;

@Component
public class BeanF extends BeanParent {

    public BeanF() {
        System.out.println("lazy bean successfully created");
    }

    public BeanF(String name, int value) {
        super(name, value);
    }

}
