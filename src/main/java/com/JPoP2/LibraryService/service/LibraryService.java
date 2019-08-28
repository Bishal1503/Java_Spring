package com.JPoP2.LibraryService.service;

import com.JPoP2.LibraryService.error.LibraryNotFoundException;
import com.JPoP2.LibraryService.model.Library;
import com.JPoP2.LibraryService.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibraryService {

    @Autowired
    LibraryRepository libraryRepository;

    public List<Library> getAllBooks()
    {
        List<Library> bookList = libraryRepository.findAll();

        if(bookList.size() > 0) {
            return bookList;
        } else {
            return new ArrayList<Library>();
        }
    }

    public Library getBookById(Long id) throws LibraryNotFoundException
    {
        Optional<Library> book = libraryRepository.findById(id);

        if(book.isPresent()) {
            return book.get();
        } else {
            throw new LibraryNotFoundException(id);
        }
    }

    public Library createOrUpdateBook(Library library) throws LibraryNotFoundException {
        Optional<Library> book = libraryRepository.findById(library.getId());

        if (book.isPresent()) {
            Library newBook = book.get();
            newBook.setCallno(library.getCallno());
            newBook.setName(library.getName());
            newBook.setAuthor(library.getAuthor());
            newBook.setPublisher(library.getPublisher());
            newBook.setQuantity(library.getQuantity());
            newBook.setIssued(library.getIssued());
            newBook.setPrice(library.getPrice());

            newBook = libraryRepository.save(newBook);
            return newBook;

        } else {
            library = libraryRepository.save(library);
            return library;
        }
    }

    public void deleteBookById(Long id) throws LibraryNotFoundException
    {
        Optional<Library> employee = libraryRepository.findById(id);

        if(employee.isPresent())
        {
            libraryRepository.deleteById(id);
        } else {
            throw new LibraryNotFoundException(id);
        }
    }
}
