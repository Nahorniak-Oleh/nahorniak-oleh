package org.epam.nahorniak.spring.homework1;

import org.epam.nahorniak.spring.homework1.config.BeansConfig;
import org.epam.nahorniak.spring.homework1.pet.Animal;
import org.epam.nahorniak.spring.homework1.pet.Cheetah;
import org.epam.nahorniak.spring.homework1.pet.Pet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {


    public static void main(String[] args) {
        //  <= Task 9
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeansConfig.class);
        Pet pet = applicationContext.getBean(Pet.class);
        pet.printPets();

        // Task 10
        System.out.println("\n-----------------Task10--------------------------");
        Animal cheetahByName = applicationContext.getBean("firstCheetah",Cheetah.class);
        Animal cheetahByType = applicationContext.getBean(Cheetah.class);
        System.out.println(cheetahByName.getPet());
        System.out.println(cheetahByType.getPet());
    }
}
