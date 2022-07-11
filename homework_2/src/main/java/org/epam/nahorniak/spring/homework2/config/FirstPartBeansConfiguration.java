package org.epam.nahorniak.spring.homework2.config;

import org.epam.nahorniak.spring.homework2.beans.BeanB;
import org.epam.nahorniak.spring.homework2.beans.BeanC;
import org.epam.nahorniak.spring.homework2.beans.BeanD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("org.epam.nahorniak.spring.homework2")
@Import(SecondPartBeansConfiguration.class)
@PropertySource("classpath:application.properties")
public class FirstPartBeansConfiguration {

    @DependsOn("beanD")
    @Bean(initMethod = "beanBInitMethod", destroyMethod = "beanBDestructionMethod")
    public BeanB beanB(@Value("${beanB.name}") final String name, @Value("${beanB.value}") final int value) {
        System.out.println("BeanB created");
        return new BeanB(name,value);
    }

    @Bean(initMethod = "beanCInitMethod", destroyMethod = "beanCDestructionMethod")
    @DependsOn("beanB")
    public BeanC beanC(@Value("${beanC.name}") final String name, @Value("${beanC.value}") final int value) {
        System.out.println("BeanC created");
        return new BeanC(name,value);
    }
}
