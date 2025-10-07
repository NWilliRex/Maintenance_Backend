package com.wam.lab1_maintenance.repository;


import com.wam.lab1_maintenance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameStartingWithIgnoreCase(String prefix);
}
