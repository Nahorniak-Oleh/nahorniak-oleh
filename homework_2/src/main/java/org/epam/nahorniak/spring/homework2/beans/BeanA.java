package org.epam.nahorniak.spring.homework2.beans;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class BeanA extends BeanParent implements InitializingBean, DisposableBean {

    @Override
    public void destroy() throws Exception {
        System.out.println("BeanA destroy method by implementing DisposableBean");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("BeanA init method by implementing InitializingBean");
    }
}
