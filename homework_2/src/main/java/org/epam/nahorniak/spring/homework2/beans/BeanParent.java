package org.epam.nahorniak.spring.homework2.beans;

public abstract class BeanParent {

    private String name;
    private int value;

    public BeanParent(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public BeanParent() {
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "\n" +
                "   • name= " + name + '\n' +
                "   • value= " + value + '\n';
    }
}
