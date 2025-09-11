package com.wam.lab1_maintenance.service;

import com.wam.lab1_maintenance.modele.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {
    public List<Person> getPersons() {
        return List.of(
                Person.builder().name("Alexandre").age(20).gender("male").build(),
                Person.builder().name("William").age(21).gender("male").build(),
                Person.builder().name("Tarek").age(18).gender("male").build(),
                Person.builder().name("Marc-Antoine").age(20).gender("male").build()
        );
    }

}
