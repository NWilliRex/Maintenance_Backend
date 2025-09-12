package com.wam.lab1_maintenance.repository;


import com.wam.lab1_maintenance.modele.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
