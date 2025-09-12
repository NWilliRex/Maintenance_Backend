package com.wam.lab1_maintenance.controller;

import com.wam.lab1_maintenance.modele.Person;
import com.wam.lab1_maintenance.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin
public class PersonController {
    private final PersonService personService;

    //cacher par allargsconstructor
    //public MaisonController(MaisonService maisonService) {
    //    this.maisonService = maisonService;
    //}

    @GetMapping("/getAll")
    public List<Person> getAllPerson() {
        return personService.getPersons();
    }

    @GetMapping("/persons/search")
    public List<Person> searchPerson(@RequestParam String name) {
        return personService.getPersons().stream().filter(person -> person.getName().startsWith(name)).collect(Collectors.toList());
    }
}
