package org.epam.nahorniak.spring.homework2.config;

import org.epam.nahorniak.spring.homework2.beans.BeanD;
import org.epam.nahorniak.spring.homework2.beans.BeanF;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
public class SecondPartBeansConfiguration {

    @Bean(initMethod = "beanDInitMethod", destroyMethod = "beanDDestructionMethod")
    public BeanD beanD(@Value("${beanD.name}") final String name, @Value("${beanD.value}") final int value) {
        System.out.println("BeanD created");
        return new BeanD(name,value);
    }

    @Lazy
    @Bean
    public BeanF beanF(){
        return new BeanF();
    }
}
