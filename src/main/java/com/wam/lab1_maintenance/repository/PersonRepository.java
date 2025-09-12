package com.wam.lab1_maintenance.repository;


import com.wam.lab1_maintenance.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByNameStartingWithIgnoreCase(String prefix);
}
