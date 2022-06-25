package org.epam.nahorniak.spring.homework2.beans;


public class BeanD extends BeanParent {

    public BeanD(String name, int value) {
        super(name, value);
    }

    public BeanD(){}

    public void beanDInitMethod(){
        System.out.println("beanD init method!");
    }

    public void beanDDestructionMethod(){
        System.out.println("beanD destruction method!");
    }

}
