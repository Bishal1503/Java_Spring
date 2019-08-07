package com.JPoP2.MicroServicesSwagger.repository;

import com.JPoP2.MicroServicesSwagger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
