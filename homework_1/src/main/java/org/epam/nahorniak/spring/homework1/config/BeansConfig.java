package org.epam.nahorniak.spring.homework1.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("org.epam.nahorniak.spring.homework1.beans")
@Import(OtherConfig.class)
public class BeansConfig {
}
