package com.JPoP2.MicroServicesSwagger.controller;

import com.JPoP2.MicroServicesSwagger.error.UserNotFoundException;
import com.JPoP2.MicroServicesSwagger.model.User;
import com.JPoP2.MicroServicesSwagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieve
     *
     * @return
     */
    @GetMapping("/users")
    List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Create
     *
     * @param newUser
     * @return
     */
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    /**
     * Retrieve
     *
     * @param id
     * @return
     */
    @GetMapping("/users/{id}")
    User findUser(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Update or Save
     *
     * @param book
     * @param id
     * @return
     */
    @PutMapping("/users/{id}")
    User updateOrSave(@RequestBody User book, @PathVariable Long id) {
        return userRepository.findById(id)
                .map(buk -> {
                    buk.setName(book.getName());
                    buk.setAuthor(book.getAuthor());
                    buk.setPrice(book.getPrice());
                    return userRepository.save(buk);
                })
                .orElseGet(() -> {
                    book.setId(id);
                    return userRepository.save(book);
                });
    }

    /**
     * Delete
     *
     * @param id
     */
    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

}
