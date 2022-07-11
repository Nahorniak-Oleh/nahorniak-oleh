package org.epam.nahorniak.spring.homework2;

import org.epam.nahorniak.spring.homework2.beans.*;
import org.epam.nahorniak.spring.homework2.config.FirstPartBeansConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(FirstPartBeansConfiguration.class);
        System.out.println("\n------------------ApplicationContext--------------------");

        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
            System.out.println(context.getBeanDefinition(beanDefinitionName));
        }

        System.out.println("------------------ApplicationContext--------------------");

        System.out.println("------------------Lazy bean ------------------");
        context.getBean(BeanF.class);

        System.out.println("------------------Beans configured by properties file");
        System.out.println(context.getBean(BeanB.class));
        System.out.println(context.getBean(BeanC.class));
        System.out.println(context.getBean(BeanD.class));

        context.close();
    }
}
