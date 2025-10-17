package com.wam.lab1_maintenance;

import com.wam.lab1_maintenance.model.Episode;
import com.wam.lab1_maintenance.model.Series;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpisodeTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        Episode episode = new Episode();
        Series series = new Series();

        episode.setId(1L);
        episode.setTitle("Pilot");
        episode.setEpisodeNumber(1);
        episode.setNote(8.5);
        episode.setSeries(series);

        assertEquals(1L, episode.getId());
        assertEquals("Pilot", episode.getTitle());
        assertEquals(1, episode.getEpisodeNumber());
        assertEquals(8.5, episode.getNote());
        assertEquals(series, episode.getSerie());
    }

    @Test
    void testAllArgsConstructor() {
        Series series = new Series();
        Episode episode = new Episode(1L, "Episode 2", 2, 7.8, series);

        assertEquals(1L, episode.getId());
        assertEquals("Episode 2", episode.getTitle());
        assertEquals(2, episode.getEpisodeNumber());
        assertEquals(7.8, episode.getNote());
        assertEquals(series, episode.getSerie());
    }

    @Test
    void testBuilderPattern() {
        Series series = new Series();
        Episode episode = Episode.builder()
                .id(10L)
                .title("Finale")
                .episodeNumber(12)
                .note(9.2)
                .serie(series)
                .build();

        assertEquals(10L, episode.getId());
        assertEquals("Finale", episode.getTitle());
        assertEquals(12, episode.getEpisodeNumber());
        assertEquals(9.2, episode.getNote());
        assertEquals(series, episode.getSerie());
    }

    @Test
    void testSetSeriesMethod() {
        Episode episode = new Episode();
        Series series = new Series();

        episode.setSeries(series);
        assertEquals(series, episode.getSerie());
    }

    @Test
    void testEqualsAndHashCode() {
        Series series = new Series();
        Episode e1 = Episode.builder()
                .id(1L)
                .title("Ep 1")
                .episodeNumber(1)
                .note(8.0)
                .serie(series)
                .build();

        Episode e2 = Episode.builder()
                .id(1L)
                .title("Ep 1")
                .episodeNumber(1)
                .note(8.0)
                .serie(series)
                .build();

        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    void testToStringContainsFields() {
        Series series = new Series();
        Episode episode = Episode.builder()
                .id(5L)
                .title("The Return")
                .episodeNumber(3)
                .note(8.3)
                .serie(series)
                .build();

        String toString = episode.toString();
        assertTrue(toString.contains("The Return"));
        assertTrue(toString.contains("3"));
        assertTrue(toString.contains("8.3"));
    }
}
