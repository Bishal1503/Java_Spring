package com.JPoP2.SpringBootCRUD;

import com.JPoP2.SpringBootCRUD.model.Book;
import com.JPoP2.SpringBootCRUD.repository.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;

@SpringBootApplication
public class SpringBootCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCrudApplication.class, args);
    }


    @Bean
    CommandLineRunner initDatabase(BookRepository bookRepository) {
        return args -> {
            bookRepository.save(new Book("The Forest of Enchantments", "Chitra Banerjee Divakaruni", new BigDecimal("353.50")));
            bookRepository.save(new Book("The Carve the Mark", "Veronica Roth", new BigDecimal("742.50")));
            bookRepository.save(new Book("The Alchemist", "Paulo Coelho", new BigDecimal("209.50")));
        };
    }

}