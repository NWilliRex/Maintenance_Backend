package com.wam.lab1_maintenance.service;

import com.wam.lab1_maintenance.model.Person;
import com.wam.lab1_maintenance.repository.PersonRepository;
import com.wam.lab1_maintenance.utils.PersonRequestBody;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    public List<Person> searchPerson(String name){
        return personRepository.findByNameStartingWithIgnoreCase(name);
    }

    public Person createPerson(PersonRequestBody body) {
        Person person = Person.builder().name(body.name()).age(body.age()).gender(body.gender()).build();
        personRepository.save(person);
        return person;
    }

    public Person updatePerson(PersonRequestBody body, long id) {
        Person person = personRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Person updatePerson = Person.builder().name(body.name()==null?person.getName():body.name()).age(body.age()==null?person.getAge(): body.age()).gender(body.gender()==null?person.getGender(): body.gender()).build();
        return personRepository.save(updatePerson);
    }

    public Person deletePerson(long id) {
        Person person = personRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        personRepository.delete(person);
        return person;
    }


}
