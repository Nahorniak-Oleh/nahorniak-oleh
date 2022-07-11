package org.epam.nahorniak.spring.homework2.beanPostProcessors;

import org.epam.nahorniak.spring.homework2.beans.BeanParent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ValidationBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof BeanParent) {
            BeanParent beanParent = (BeanParent) bean;

            if (Objects.isNull(beanParent.getName())) {
                System.out.println("name must be not null");
            }

            if (beanParent.getValue() <= 0) {
                System.out.println("value must be bigger than 0");
            }

            System.out.println("validation for " + beanName + " was successfully performed");
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
