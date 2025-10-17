package com.wam.lab1_maintenance;

import com.wam.lab1_maintenance.model.Gender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenderTest {

    @Test
    void testEnumValuesCount() {
        Gender[] values = Gender.values();
        assertEquals(3, values.length, "Gender enum should have 3 values");
    }

    @Test
    void testEnumValuesOrder() {
        Gender[] values = Gender.values();
        assertArrayEquals(
                new Gender[]{Gender.MALE, Gender.FEMALE, Gender.OTHER},
                values,
                "Enum constants should be in declared order"
        );
    }

    @Test
    void testValueOf() {
        assertEquals(Gender.MALE, Gender.valueOf("MALE"));
        assertEquals(Gender.FEMALE, Gender.valueOf("FEMALE"));
        assertEquals(Gender.OTHER, Gender.valueOf("OTHER"));
    }

    @Test
    void testValueOf_InvalidValue_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Gender.valueOf("UNKNOWN"));
    }

    @Test
    void testToStringReturnsName() {
        assertEquals("MALE", Gender.MALE.toString());
        assertEquals("FEMALE", Gender.FEMALE.toString());
        assertEquals("OTHER", Gender.OTHER.toString());
    }
}
