package org.epam.nahorniak.spring.homework1.other;

import org.epam.nahorniak.spring.homework1.beans.BeanB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OtherBeanB {

    private BeanB beanB;

    @Autowired
    public void setBeanB(BeanB beanB) {
        System.out.println(this.getClass().getSimpleName()+ ". " + beanB.getClass().getSimpleName()+ " was injected through the setter");
        this.beanB = beanB;
    }
}
