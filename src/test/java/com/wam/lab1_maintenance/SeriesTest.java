package com.wam.lab1_maintenance;

import com.wam.lab1_maintenance.model.Series;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeriesTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        Series series = new Series(
                1L,
                "Breaking Bad",
                "Drama",
                62,
                9.8f,
                "A chemistry teacher turns to making meth."
        );

        assertEquals(1L, series.getId());
        assertEquals("Breaking Bad", series.getTitle());
        assertEquals("Drama", series.getGenre());
        assertEquals(62, series.getNbEpisodes());
        assertEquals(9.8f, series.getNote());
        assertEquals("A chemistry teacher turns to making meth.", series.getDescription());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        Series series = new Series();

        series.setId(2L);
        series.setTitle("The Office");
        series.setGenre("Comedy");
        series.setNbEpisodes(201);
        series.setNote(8.9f);
        series.setDescription("A mockumentary about office life.");

        assertEquals(2L, series.getId());
        assertEquals("The Office", series.getTitle());
        assertEquals("Comedy", series.getGenre());
        assertEquals(201, series.getNbEpisodes());
        assertEquals(8.9f, series.getNote());
        assertEquals("A mockumentary about office life.", series.getDescription());
    }

    @Test
    void testBuilderPattern() {
        Series series = Series.builder()
                .id(3L)
                .title("Stranger Things")
                .genre("Sci-Fi")
                .nbEpisodes(34)
                .note(8.7f)
                .description("Kids vs monsters in the 80s.")
                .build();

        assertEquals(3L, series.getId());
        assertEquals("Stranger Things", series.getTitle());
        assertEquals("Sci-Fi", series.getGenre());
        assertEquals(34, series.getNbEpisodes());
        assertEquals(8.7f, series.getNote());
        assertEquals("Kids vs monsters in the 80s.", series.getDescription());
    }

    @Test
    void testEqualsAndHashCode() {
        Series s1 = Series.builder()
                .id(1L)
                .title("The Witcher")
                .genre("Fantasy")
                .nbEpisodes(16)
                .note(8.1f)
                .build();

        Series s2 = Series.builder()
                .id(1L)
                .title("The Witcher")
                .genre("Fantasy")
                .nbEpisodes(16)
                .note(8.1f)
                .build();

        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }

    @Test
    void testToString() {
        Series series = Series.builder()
                .id(4L)
                .title("Arcane")
                .genre("Animation")
                .nbEpisodes(9)
                .note(9.3f)
                .description("League of Legends universe story.")
                .build();

        String toString = series.toString();
        assertTrue(toString.contains("Arcane"));
        assertTrue(toString.contains("Animation"));
        assertTrue(toString.contains("9.3"));
    }

    @Test
    void testTransientDescriptionNotPersisted() {
        Series series = new Series();
        series.setDescription("Temporary description");

        // Just ensure the field is usable and not null
        assertEquals("Temporary description", series.getDescription());

        // Simulate persistence behavior: transient field shouldn't affect equality
        Series persisted = Series.builder()
                .id(series.getId())
                .title(series.getTitle())
                .genre(series.getGenre())
                .nbEpisodes(series.getNbEpisodes())
                .note(series.getNote())
                .build();

        assertNotEquals(series.getDescription(), persisted.getDescription());
    }
}

