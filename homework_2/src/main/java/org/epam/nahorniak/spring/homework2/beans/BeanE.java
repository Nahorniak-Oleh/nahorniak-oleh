package org.epam.nahorniak.spring.homework2.beans;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class BeanE extends BeanParent {

    public BeanE() {
    }

    @PostConstruct
    public void initMethod() {
        System.out.println("BeanE init method using PostConstruct annotation");
    }

    @PreDestroy
    public void destroyMethod() {
        System.out.println("BeanE destroy method using PreDestroy annotation");
    }

}
