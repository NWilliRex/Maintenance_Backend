package com.wam.lab1_maintenance.controller;

import com.wam.lab1_maintenance.model.Person;
import com.wam.lab1_maintenance.service.PersonService;
import com.wam.lab1_maintenance.utils.PersonRequestBody;
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
        return personService.searchPerson(name);
    }

    @PostMapping("/persons")
    public Person createPerson(@RequestBody PersonRequestBody body) {
        return personService.createPerson(body);
    }

    @PutMapping("/persons/{id}")
    public Person updatePerson(@PathVariable Integer id, @RequestBody PersonRequestBody body) {
        return personService.updatePerson(body, id);
    }
    @DeleteMapping("/persons/{id}")
    public Person deletePerson(@PathVariable Integer id) {
        return personService.deletePerson(id);
    }
}
