package com.wam.lab1_maintenance;

import com.wam.lab1_maintenance.model.Role;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void testEnumValues() {
        Role[] roles = Role.values();

        assertEquals(2, roles.length);
        assertTrue(List.of(roles).contains(Role.USER));
        assertTrue(List.of(roles).contains(Role.ADMIN));
    }

    @Test
    void testValueOfUser() {
        Role role = Role.valueOf("USER");
        assertEquals(Role.USER, role);
    }

    @Test
    void testValueOfAdmin() {
        Role role = Role.valueOf("ADMIN");
        assertEquals(Role.ADMIN, role);
    }

    @Test
    void testInvalidRoleThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Role.valueOf("INVALID"));
    }

    @Test
    void testEnumNamesMatch() {
        assertEquals("USER", Role.USER.name());
        assertEquals("ADMIN", Role.ADMIN.name());
    }
}
