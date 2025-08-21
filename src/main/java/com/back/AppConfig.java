package com.back;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    PersonRepository personRepository() {
        return new PersonRepository(1);
    }

    @Bean
    int version() {
        return 3;
    }

    @Bean
    public ApplicationRunner myApplicationRunner() {
        return new MyApplicationRunner();
    }
    @Bean
    public ApplicationRunner myApplicationRunner2() {
        return new MyApplicationRunner();
    }
}
