package com.wam.lab1_maintenance.service;

import com.wam.lab1_maintenance.model.User;
import com.wam.lab1_maintenance.repository.UserRepository;
import com.wam.lab1_maintenance.request.PersonRequestBody;
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
        return userRepository.findByFnameStartingWithIgnoreCase(name);
    }

    public User createPerson(PersonRequestBody body) {
        User user = User.builder()
                .fname(body.fname())
                .lname(body.lname())
                .age(body.age())
                .gender(body.gender())
                .build();
        return userRepository.save(user);
    }

    public User updatePerson(PersonRequestBody body, Long id) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        user.setFname(body.fname() != null ? body.fname() : user.getFname());
        user.setLname(body.lname() != null ? body.lname() : user.getLname());
        user.setAge(body.age() != null ? body.age() : user.getAge());
        user.setGender(body.gender() != null ? body.gender() : user.getGender());
        return userRepository.save(user);
    }

    public User deletePerson(Long id) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userRepository.delete(user);
        return user;
    }
}
