package com.wam.lab1_maintenance.service;

import com.wam.lab1_maintenance.model.User;
import com.wam.lab1_maintenance.repository.UserRepository;
import com.wam.lab1_maintenance.utils.PersonRequestBody;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getPersons() {
        return userRepository.findAll();
    }

    public List<User> searchPerson(String name){
        return userRepository.findByNameStartingWithIgnoreCase(name);
    }

    public User createPerson(PersonRequestBody body) {
        User user = User.builder().name(body.name()).age(body.age()).gender(body.gender()).build();
        userRepository.save(user);
        return user;
    }

    public User updatePerson(PersonRequestBody body, long id) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        User updateUser = User.builder().name(body.name()==null? user.getName():body.name()).age(body.age()==null? user.getAge(): body.age()).gender(body.gender()==null? user.getGender(): body.gender()).build();
        return userRepository.save(updateUser);
    }

    public User deletePerson(long id) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userRepository.delete(user);
        return user;
    }


}
