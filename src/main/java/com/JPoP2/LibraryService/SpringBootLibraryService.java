package com.JPoP2.LibraryService;

import com.JPoP2.LibraryService.model.Library;
import com.JPoP2.LibraryService.repository.LibraryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class SpringBootLibraryService {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLibraryService.class, args);
    }


    @Bean
    CommandLineRunner initDatabase(LibraryRepository libraryRepository) {
        return args -> {
            libraryRepository.save(new Library(1L,"A@2", "The Forest of Enchantments", "Chitra Banerjee Divakaruni", "BPB", 2, 2, new BigDecimal("353.50")));
            libraryRepository.save(new Library(2L, "B@1", "The Forest of Enchantments", "Chitra Banerjee Divakaruni", "Pearson", 3, 0, new BigDecimal("353.50")));
            libraryRepository.save(new Library(3L, "G@12", "The Carve the Mark", "Veronica Roth", "BPB", 10, 0, new BigDecimal("742.50")));
            libraryRepository.save(new Library(4L, "W@34", "The Alchemist", "Paulo Coelho", "Pearson", 12, 2, new BigDecimal("209.50")));
        };
    }

}