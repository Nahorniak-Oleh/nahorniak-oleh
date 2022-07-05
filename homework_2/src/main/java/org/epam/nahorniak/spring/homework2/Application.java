package org.epam.nahorniak.spring.homework2;

import org.epam.nahorniak.spring.homework2.beans.*;
import org.epam.nahorniak.spring.homework2.config.FirstPartBeansConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(FirstPartBeansConfiguration.class);
        System.out.println("\n------------------ApplicationContext--------------------");

        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }
        System.out.println("------------------ApplicationContext--------------------");

        System.out.println("------------------Lazy bean ------------------");
        applicationContext.getBean(BeanF.class);

        System.out.println("------------------Beans configured by properties file");
        System.out.println(applicationContext.getBean(BeanB.class));
        System.out.println(applicationContext.getBean(BeanC.class));
        System.out.println(applicationContext.getBean(BeanD.class));

    }
}
