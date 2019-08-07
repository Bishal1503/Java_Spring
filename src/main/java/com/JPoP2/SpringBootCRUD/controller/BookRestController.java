package com.JPoP2.SpringBootCRUD.controller;

import com.JPoP2.SpringBootCRUD.error.BookNotFoundException;
import com.JPoP2.SpringBootCRUD.model.Book;
import com.JPoP2.SpringBootCRUD.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookRestController {

    @Autowired
    private BookRepository bookRepository;

    /**
     * Retrieve
     *
     * @return
     */
    @GetMapping("/books")
    List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * Create
     *
     * @param newBook
     * @return
     */
    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    Book newBook(@RequestBody Book newBook) {
        return bookRepository.save(newBook);
    }

    /**
     * Retrieve
     *
     * @param id
     * @return
     */
    @GetMapping("/books/{id}")
    Book findBook(@PathVariable Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    /**
     * Update or Save
     *
     * @param book
     * @param id
     * @return
     */
    @PutMapping("/books/{id}")
    Book updateOrSave(@RequestBody Book book, @PathVariable Long id) {
        return bookRepository.findById(id)
                .map(buk -> {
                    buk.setName(book.getName());
                    buk.setAuthor(book.getAuthor());
                    buk.setPrice(book.getPrice());
                    return bookRepository.save(buk);
                })
                .orElseGet(() -> {
                    book.setId(id);
                    return bookRepository.save(book);
                });
    }

    /**
     * Delete
     *
     * @param id
     */
    @DeleteMapping("/books/{id}")
    void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }

}
