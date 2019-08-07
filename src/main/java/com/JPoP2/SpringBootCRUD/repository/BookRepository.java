package com.JPoP2.SpringBootCRUD.repository;

import com.JPoP2.SpringBootCRUD.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
