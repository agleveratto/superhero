package com.agleveratto.superhero.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.agleveratto.superhero.infrastructure.repositories")
public class SuperheroApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperheroApplication.class, args);
    }

}
