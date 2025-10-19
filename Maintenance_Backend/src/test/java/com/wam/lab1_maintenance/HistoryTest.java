package com.wam.lab1_maintenance;

import com.wam.lab1_maintenance.model.Episode;
import com.wam.lab1_maintenance.model.History;
import com.wam.lab1_maintenance.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HistoryTest {

    @Test
    void testBuilderAndAllArgsConstructor() {
        User user = User.builder().id(1L).fname("John").build();
        Episode episode = Episode.builder().id(5L).title("Pilot").build();
        LocalDateTime watchedAt = LocalDateTime.now();

        History history = History.builder()
                .id(100L)
                .user(user)
                .episode(episode)
                .watchedAt(watchedAt)
                .build();

        assertEquals(100L, history.getId());
        assertEquals(user, history.getUser());
        assertEquals(episode, history.getEpisode());
        assertEquals(watchedAt, history.getWatchedAt());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        User user = User.builder().id(2L).fname("Alice").build();
        Episode episode = Episode.builder().id(6L).title("Finale").build();
        LocalDateTime now = LocalDateTime.now();

        History history = new History();
        history.setId(200L);
        history.setUser(user);
        history.setEpisode(episode);
        history.setWatchedAt(now);

        assertEquals(200L, history.getId());
        assertEquals(user, history.getUser());
        assertEquals(episode, history.getEpisode());
        assertEquals(now, history.getWatchedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime date = LocalDateTime.of(2024, 5, 1, 12, 0);

        History h1 = History.builder().id(1L).watchedAt(date).build();
        History h2 = History.builder().id(1L).watchedAt(date).build();

        assertEquals(h1, h2);
        assertEquals(h1.hashCode(), h2.hashCode());
    }

    @Test
    void testToStringContainsImportantFields() {
        LocalDateTime date = LocalDateTime.of(2025, 10, 10, 15, 0);
        History history = History.builder()
                .id(77L)
                .watchedAt(date)
                .build();

        String result = history.toString();
        assertTrue(result.contains("id=77"));
        assertTrue(result.contains("watchedAt="));
    }

    @Test
    void testBuilderCreatesDistinctObjects() {
        History h1 = History.builder().id(1L).build();
        History h2 = History.builder().id(2L).build();

        assertNotEquals(h1, h2);
    }
}
