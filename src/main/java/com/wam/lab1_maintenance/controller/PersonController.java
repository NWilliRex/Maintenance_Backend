package com.wam.lab1_maintenance.controller;

import com.wam.lab1_maintenance.modele.Person;
import com.wam.lab1_maintenance.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
