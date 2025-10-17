package com.wam.lab1_maintenance;

import com.wam.lab1_maintenance.model.Gender;
import com.wam.lab1_maintenance.model.User;
import com.wam.lab1_maintenance.repository.UserRepository;
import com.wam.lab1_maintenance.request.PersonRequestBody;
import com.wam.lab1_maintenance.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = User.builder()
                .id(1L)
                .fname("Alex")
                .lname("Rus")
                .age(21)
                .gender(Gender.MALE)
                .build();
    }

    // ✅ getPersons()
    @Test
    void testGetPersons_ShouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(sampleUser));

        List<User> result = userService.getPersons();

        assertEquals(1, result.size());
        assertEquals("Alex", result.get(0).getFname());
        verify(userRepository, times(1)).findAll();
    }

    // ✅ searchPerson()
    @Test
    void testSearchPerson_ShouldCallRepositoryAndReturnList() {
        when(userRepository.findByFnameStartingWithIgnoreCase("Al"))
                .thenReturn(List.of(sampleUser));

        List<User> result = userService.searchPerson("Al");

        assertEquals(1, result.size());
        assertEquals("Alex", result.get(0).getFname());
        verify(userRepository, times(1)).findByFnameStartingWithIgnoreCase("Al");
    }

    // ✅ createPerson()
    @Test
    void testCreatePerson_ShouldSaveAndReturnUser() {
        PersonRequestBody body = new PersonRequestBody("Marie", "Dupont", 25, Gender.FEMALE);

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.createPerson(body);

        assertEquals("Marie", result.getFname());
        assertEquals("Dupont", result.getLname());
        assertEquals(25, result.getAge());
        assertEquals(Gender.FEMALE, result.getGender());
        verify(userRepository, times(1)).save(any(User.class));
    }

    // ✅ updatePerson() - normal update
    @Test
    void testUpdatePerson_ShouldUpdateExistingUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        PersonRequestBody body = new PersonRequestBody("Alexis", null, 22, null);

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.updatePerson(body, 1L);

        assertEquals("Alexis", result.getFname());
        assertEquals("Rus", result.getLname());
        assertEquals(22, result.getAge());
        assertEquals(Gender.MALE, result.getGender());
        verify(userRepository, times(1)).save(any(User.class));
    }

    // ✅ updatePerson() - not found
    @Test
    void testUpdatePerson_NotFound_ShouldThrowException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        PersonRequestBody body = new PersonRequestBody("Name", "Last", 20, Gender.FEMALE);

        assertThrows(EntityNotFoundException.class, () -> userService.updatePerson(body, 99L));
        verify(userRepository, never()).save(any());
    }

    // ✅ deletePerson() - normal case
    @Test
    void testDeletePerson_ShouldDeleteAndReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        User result = userService.deletePerson(1L);

        assertEquals(sampleUser, result);
        verify(userRepository, times(1)).delete(sampleUser);
    }

    // ✅ deletePerson() - not found
    @Test
    void testDeletePerson_NotFound_ShouldThrowException() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.deletePerson(2L));
        verify(userRepository, never()).delete(any());
    }

    // ✅ updatePerson() - when all fields null (should keep old values)
    @Test
    void testUpdatePerson_AllNullFields_ShouldKeepExistingValues() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        PersonRequestBody body = new PersonRequestBody(null, null, null, null);
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.updatePerson(body, 1L);

        assertEquals("Alex", result.getFname());
        assertEquals("Rus", result.getLname());
        assertEquals(21, result.getAge());
        assertEquals(Gender.MALE, result.getGender());
    }
}
