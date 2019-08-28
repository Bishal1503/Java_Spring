package com.JPoP2.LibraryService.controller;

import com.JPoP2.LibraryService.error.LibraryNotFoundException;
import com.JPoP2.LibraryService.model.Library;
import com.JPoP2.LibraryService.repository.LibraryRepository;
import com.JPoP2.LibraryService.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LibraryRestController {

    @Autowired
    private LibraryRepository libraryRepository;
    @Autowired
    private LibraryService libraryService;

    /**
     * Login
     *
     * @return
     */
    @GetMapping("/lib")
    List<Library> findAllById() {
        return libraryRepository.getOne(1L);
    }

    /**
     * List of all books
     *
     * @return
     */
    @GetMapping("/lib/books")
    List<Library> findAll() {
        return libraryService.getAllBooks();
    }
    public ResponseEntity<List<Library>> getAllBooks(){
        List<Library> list = libraryService.getAllBooks();
        return new ResponseEntity<List<Library>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Retrieve a book's details
     *
     * @param id
     * @return
     * @throws LibraryNotFoundException
     */
    @GetMapping("lib/books/{book_id}")
    public ResponseEntity<Library> findBookById(@PathVariable("id") Long id)
            throws LibraryNotFoundException {
        Library lib = libraryService.getBookById(id);

        return new ResponseEntity<Library>(lib, new HttpHeaders(), HttpStatus.OK);
    }


    /**
     * Add a new book
     *
     * @param library
     * @return
     * @throws LibraryNotFoundException
     */
    @PostMapping("lib/books/{book_id}")
    public ResponseEntity<Library> createBookEntry(Library library)
            throws LibraryNotFoundException {
        Library updated = libraryService.createOrUpdateBook(library);
        return new ResponseEntity<Library>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Delete a book
     *
     * @param id
     * @return
     * @throws LibraryNotFoundException
     */
    @DeleteMapping("lib/books/{book_id}}")
    public HttpStatus deleteBookById(@PathVariable("id") Long id) throws LibraryNotFoundException{
        libraryService.deleteBookById(id);
        return HttpStatus.FORBIDDEN;
    }


    /**
     * Update or Save
     *
     * @param library
     * @param id
     * @return
     */
    @PutMapping("lib/{id}")
    Library updateOrSave(@RequestBody Library library, @PathVariable Long id) {
        return libraryRepository.findById(id)
                .map(lib -> {
                    lib.setCallno(library.getCallno());
                    lib.setName(library.getName());
                    lib.setAuthor(library.getAuthor());
                    lib.setPublisher(library.getPublisher());
                    lib.setQuantity(library.getQuantity());
                    lib.setIssued(library.getIssued());
                    lib.setPrice(library.getPrice());
                    return libraryRepository.save(lib);
                })
                .orElseGet(() -> {
                    library.setId(id);
                    return libraryRepository.save(library);
                });
    }



}
