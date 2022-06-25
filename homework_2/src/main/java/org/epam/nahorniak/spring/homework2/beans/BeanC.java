package org.epam.nahorniak.spring.homework2.beans;

public class BeanC extends BeanParent {
    public BeanC(String name, int value) {
        super(name, value);
    }

    public BeanC(){}

    public void beanCInitMethod(){
        System.out.println("beanC init method!");
    }

    public void beanCDestructionMethod(){
        System.out.println("beanC destruction method!");
    }



}
