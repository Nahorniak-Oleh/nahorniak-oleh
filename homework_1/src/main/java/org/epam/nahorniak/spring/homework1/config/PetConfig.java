package org.epam.nahorniak.spring.homework1.config;

import org.epam.nahorniak.spring.homework1.pet.Animal;
import org.epam.nahorniak.spring.homework1.pet.Cheetah;
import org.epam.nahorniak.spring.homework1.pet.Spider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = {"org.epam.nahorniak.spring.homework1"}, excludeFilters={
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= Spider.class)})
public class PetConfig {

    @Primary
    @Bean
    public Animal firstCheetah(){
        return new Cheetah();
    }

    @Qualifier("cheetah2")
    @Bean
    public Animal secondCheetah(){
        return new Cheetah();
    }

}
