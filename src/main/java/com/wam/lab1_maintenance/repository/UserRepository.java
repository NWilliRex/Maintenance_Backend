package com.wam.lab1_maintenance.repository;


import com.wam.lab1_maintenance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameStartingWithIgnoreCase(String prefix);

    Optional<User> findByCredentialUsername(String username);
}
