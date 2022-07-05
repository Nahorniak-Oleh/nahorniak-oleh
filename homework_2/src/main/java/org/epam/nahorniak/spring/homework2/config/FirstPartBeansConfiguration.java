package org.epam.nahorniak.spring.homework2.config;

import org.epam.nahorniak.spring.homework2.beans.BeanB;
import org.epam.nahorniak.spring.homework2.beans.BeanC;
import org.epam.nahorniak.spring.homework2.beans.BeanD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("org.epam.nahorniak.spring.homework2")
@Import(SecondPartBeansConfiguration.class)
@PropertySource("classpath:application.properties")
public class FirstPartBeansConfiguration {

    private static final Integer ZERO_NUMBER = 0;
    private final Environment environment;

    @Autowired
    public FirstPartBeansConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Bean(initMethod = "beanCInitMethod", destroyMethod = "beanCDestructionMethod")
    @DependsOn({"beanD", "beanB"})
    public BeanC beanC() {
        System.out.println("BeanC created");
        return new BeanC(
                environment.getProperty("beanC.name"),
                getIntegerValueFromPropertyFile(environment.getProperty("beanC.value")));
    }

    @Bean(initMethod = "beanDInitMethod", destroyMethod = "beanDDestructionMethod")
    public BeanD beanD() {
        System.out.println("BeanD created");
        return new BeanD(
                environment.getProperty("beanD.name"),
                getIntegerValueFromPropertyFile(environment.getProperty("beanD.value")));
    }

    @Bean(initMethod = "beanBInitMethod", destroyMethod = "beanBDestructionMethod")
    public BeanB beanB() {
        System.out.println("BeanB created");
        return new BeanB(
                environment.getProperty("beanB.name"),
                getIntegerValueFromPropertyFile(environment.getProperty("beanB.value")));
    }

    private Integer getIntegerValueFromPropertyFile(String value) {
        return value.matches("\\D+") ? ZERO_NUMBER : Integer.valueOf(value);
    }

}
