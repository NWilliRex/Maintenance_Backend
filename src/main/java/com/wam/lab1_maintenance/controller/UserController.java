package com.wam.lab1_maintenance.controller;

import com.wam.lab1_maintenance.model.User;
import com.wam.lab1_maintenance.service.UserService;
import com.wam.lab1_maintenance.utils.PersonRequestBody;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;


    @GetMapping("/getAll")
    public List<User> getAllPerson() {
        return userService.getPersons();
    }

    @GetMapping("/persons/search")
    public List<User> searchPerson(@RequestParam String name) {
        return userService.searchPerson(name);
    }

    @PostMapping("/persons")
    public User createPerson(@RequestBody PersonRequestBody body) {
        return userService.createPerson(body);
    }

    @PutMapping("/persons/{id}")
    public User updatePerson(@PathVariable Integer id, @RequestBody PersonRequestBody body) {
        return userService.updatePerson(body, id);
    }
    @DeleteMapping("/persons/{id}")
    public User deletePerson(@PathVariable Integer id) {
        return userService.deletePerson(id);
    }
}
