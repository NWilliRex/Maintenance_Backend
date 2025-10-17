package com.wam.lab1_maintenance;

import com.wam.lab1_maintenance.model.Credential;
import com.wam.lab1_maintenance.model.Gender;
import com.wam.lab1_maintenance.model.Role;
import com.wam.lab1_maintenance.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .fname("Alex")
                .lname("Rus")
                .age(21)
                .gender(Gender.MALE)
                .role(Role.USER)
                .credential(Credential.builder()
                        .username("alex")
                        .password("1234")
                        .isActive(true)
                        .build())
                .build();
    }

    // ✅ Basic getters
    @Test
    void testGetters_ShouldReturnCorrectValues() {
        assertEquals(1L, user.getId());
        assertEquals("Alex", user.getFname());
        assertEquals("Rus", user.getLname());
        assertEquals(21, user.getAge());
        assertEquals(Gender.MALE, user.getGender());
        assertEquals(Role.USER, user.getRole());
        assertNotNull(user.getCredential());
    }

    // ✅ setUsername() when credential already exists
    @Test
    void testSetUsername_WhenCredentialExists_ShouldUpdateIt() {
        user.setUsername("newUser");
        assertEquals("newUser", user.getCredential().getUsername());
    }

    // ✅ setUsername() when credential is null
    @Test
    void testSetUsername_WhenCredentialIsNull_ShouldCreateNewCredential() {
        user.setCredential(null);

        user.setUsername("createdUser");

        assertNotNull(user.getCredential());
        assertEquals("createdUser", user.getCredential().getUsername());
        assertTrue(user.getCredential().getIsActive());
    }

    // ✅ setPassword() when credential already exists
    @Test
    void testSetPassword_WhenCredentialExists_ShouldUpdateIt() {
        user.setPassword("newPass");
        assertEquals("newPass", user.getCredential().getPassword());
    }

    // ✅ setPassword() when credential is null
    @Test
    void testSetPassword_WhenCredentialIsNull_ShouldCreateNewCredential() {
        user.setCredential(null);

        user.setPassword("securePass");

        assertNotNull(user.getCredential());
        assertEquals("securePass", user.getCredential().getPassword());
        assertTrue(user.getCredential().getIsActive());
    }

    // ✅ setRole(String)
    @Test
    void testSetRole_ShouldConvertStringToEnum() {
        user.setRole("admin");
        assertEquals(Role.ADMIN, user.getRole());

        user.setRole("USER");
        assertEquals(Role.USER, user.getRole());

        user.setRole(null);
        assertNull(user.getRole());
    }

    // ✅ getAuthorities()
    @Test
    void testGetAuthorities_ShouldReturnCorrectAuthority() {
        user.setRole("ADMIN");
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertEquals(1, authorities.size());
        assertEquals("ADMIN", authorities.iterator().next().getAuthority());
    }

    @Test
    void testGetAuthorities_WhenRoleIsNull_ShouldReturnEmptyList() {
        user.setRole(null);
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertTrue(authorities.isEmpty());
    }

    // ✅ getUsername()
    @Test
    void testGetUsername_ShouldReturnFromCredential() {
        assertEquals("alex", user.getUsername());
    }

    @Test
    void testGetUsername_WhenCredentialIsNull_ShouldReturnNull() {
        user.setCredential(null);
        assertNull(user.getUsername());
    }

    // ✅ getPassword()
    @Test
    void testGetPassword_ShouldReturnFromCredential() {
        assertEquals("1234", user.getPassword());
    }

    @Test
    void testGetPassword_WhenCredentialIsNull_ShouldReturnNull() {
        user.setCredential(null);
        assertNull(user.getPassword());
    }

    // ✅ isEnabled()
    @Test
    void testIsEnabled_WhenCredentialActive_ShouldReturnTrue() {
        assertTrue(user.isEnabled());
    }

    @Test
    void testIsEnabled_WhenCredentialInactive_ShouldReturnFalse() {
        user.getCredential().setIsActive(false);
        assertFalse(user.isEnabled());
    }

    @Test
    void testIsEnabled_WhenCredentialNull_ShouldReturnFalse() {
        user.setCredential(null);
        assertFalse(user.isEnabled());
    }

    // ✅ Security interface defaults
    @Test
    void testAccountStatusMethods_ShouldAlwaysReturnTrue() {
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
    }

    // ✅ Builder and toString() (sanity check)
    @Test
    void testBuilderAndToString_ShouldCreateProperUser() {
        User built = User.builder()
                .fname("John")
                .lname("Doe")
                .age(30)
                .role(Role.USER)
                .build();

        assertEquals("John", built.getFname());
        assertEquals(Role.USER, built.getRole());
        assertTrue(built.toString().contains("John"));
    }
}

