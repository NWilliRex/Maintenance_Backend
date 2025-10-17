package com.wam.lab1_maintenance.repository;

import com.wam.lab1_maintenance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByFnameStartingWithIgnoreCase(String prefix);

    Optional<User> findByCredentialUsername(String username);
}
