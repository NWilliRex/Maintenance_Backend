package com.wam.lab1_maintenance;

import com.wam.lab1_maintenance.model.Credential;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CredentialTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        Credential credential = new Credential();

        credential.setId(1L);
        credential.setUsername("alex_user");
        credential.setPassword("hashed_pass");
        credential.setIsActive(true);

        assertEquals(1L, credential.getId());
        assertEquals("alex_user", credential.getUsername());
        assertEquals("hashed_pass", credential.getPassword());
        assertTrue(credential.getIsActive());
    }

    @Test
    void testAllArgsConstructor() {
        Credential credential = new Credential(2L, "john_doe", "pass123", false);

        assertEquals(2L, credential.getId());
        assertEquals("john_doe", credential.getUsername());
        assertEquals("pass123", credential.getPassword());
        assertFalse(credential.getIsActive());
    }

    @Test
    void testBuilderPattern() {
        Credential credential = Credential.builder()
                .id(3L)
                .username("builder_user")
                .password("builder_pass")
                .isActive(true)
                .build();

        assertEquals(3L, credential.getId());
        assertEquals("builder_user", credential.getUsername());
        assertEquals("builder_pass", credential.getPassword());
        assertTrue(credential.getIsActive());
    }

    @Test
    void testEqualsAndHashCode() {
        Credential c1 = Credential.builder()
                .id(1L)
                .username("same_user")
                .password("same_pass")
                .isActive(true)
                .build();

        Credential c2 = Credential.builder()
                .id(1L)
                .username("same_user")
                .password("same_pass")
                .isActive(true)
                .build();

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testNotEqualsDifferentFields() {
        Credential c1 = Credential.builder()
                .id(1L)
                .username("user1")
                .password("pass1")
                .isActive(true)
                .build();

        Credential c2 = Credential.builder()
                .id(2L)
                .username("user2")
                .password("pass2")
                .isActive(false)
                .build();

        assertNotEquals(c1, c2);
    }

    @Test
    void testToStringContainsFields() {
        Credential credential = Credential.builder()
                .id(10L)
                .username("test_user")
                .password("secret")
                .isActive(true)
                .build();

        String result = credential.toString();
        assertTrue(result.contains("test_user"));
        assertTrue(result.contains("true"));
    }
}
