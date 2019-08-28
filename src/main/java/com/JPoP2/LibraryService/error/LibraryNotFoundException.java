package com.JPoP2.LibraryService.error;

public class LibraryNotFoundException extends RuntimeException {
    public LibraryNotFoundException(Long id) {
        super("Book not found in Library: " + id);
    }
}
