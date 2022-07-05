package org.epam.nahorniak.spring.homework2.beans;

import org.epam.nahorniak.spring.homework2.validators.MyValidator;

public abstract class BeanParent implements MyValidator {

    private String name;
    private int value;

    public BeanParent(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public BeanParent() {
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "\n" +
                "   • name= " + name + '\n' +
                "   • value= " + value + '\n';
    }

    @Override
    public void validate() {
        if (name == null) name = "default name";
        if (value <= 0) value = 1;
    }

}
