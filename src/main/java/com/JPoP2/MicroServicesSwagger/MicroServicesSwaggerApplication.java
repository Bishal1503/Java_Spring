package com.JPoP2.MicroServicesSwagger;

import com.JPoP2.MicroServicesSwagger.model.User;
import com.JPoP2.MicroServicesSwagger.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;

@SpringBootApplication
public class MicroServicesSwaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroServicesSwaggerApplication.class, args);
    }


    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            userRepository.save(new User("The Forest of Enchantments", "Chitra Banerjee Divakaruni", new BigDecimal("353.50")));
            userRepository.save(new User("The Carve the Mark", "Veronica Roth", new BigDecimal("742.50")));
            userRepository.save(new User("The Alchemist", "Paulo Coelho", new BigDecimal("209.50")));
        };
    }

}