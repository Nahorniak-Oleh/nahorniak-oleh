package org.epam.nahorniak.spring.homework2.beans;

public class BeanB extends BeanParent {

    public BeanB(String name, int value) {
        super(name, value);
    }

    public BeanB() {
    }

    public void beanBInitMethod(){
        System.out.println("beanB init method!");
    }

    public void beanBDestructionMethod(){
        System.out.println("beanB destruction method!");
    }

    public void beanBAdditionalInitMethod(){ System.out.println("additional beanB init method!"); }
}
