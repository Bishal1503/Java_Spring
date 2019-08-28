package com.JPoP2.LibraryService.repository;

import com.JPoP2.LibraryService.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, Long> {
}
