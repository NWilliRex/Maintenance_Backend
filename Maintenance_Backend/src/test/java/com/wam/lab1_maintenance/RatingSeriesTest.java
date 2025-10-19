package com.wam.lab1_maintenance;

import com.wam.lab1_maintenance.model.RatingSeries;
import com.wam.lab1_maintenance.model.Series;
import com.wam.lab1_maintenance.model.User;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RatingSeriesTest {

    @Test
    void testBuilderAndFields() {
        User user = User.builder().id(1L).fname("John").build();
        Series series = Series.builder().id(2L).title("Breaking Bad").build();
        LocalDateTime now = LocalDateTime.now();

        RatingSeries rating = RatingSeries.builder()
                .id(10)
                .user(user)
                .series(series)
                .userRating(4.5f)
                .ratedAt(now)
                .build();

        assertEquals(10, rating.getId());
        assertEquals(user, rating.getUser());
        assertEquals(series, rating.getSeries());
        assertEquals(4.5f, rating.getUserRating());
        assertEquals(now, rating.getRatedAt());
    }

    @Test
    void testSetRatingWithInt() {
        RatingSeries rating = new RatingSeries();
        rating.setRating(5);

        assertEquals(5.0f, rating.getUserRating());
    }

    @Test
    void testSetRatingWithFloat() {
        RatingSeries rating = new RatingSeries();
        rating.setRating(3.7f);

        assertEquals(3.7f, rating.getUserRating());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        RatingSeries rating = new RatingSeries();
        User user = User.builder().id(1L).fname("Jane").build();
        Series series = Series.builder().id(2L).title("Friends").build();
        LocalDateTime date = LocalDateTime.now();

        rating.setId(100);
        rating.setUser(user);
        rating.setSeries(series);
        rating.setUserRating(4.2f);
        rating.setRatedAt(date);

        assertEquals(100, rating.getId());
        assertEquals(user, rating.getUser());
        assertEquals(series, rating.getSeries());
        assertEquals(4.2f, rating.getUserRating());
        assertEquals(date, rating.getRatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        RatingSeries r1 = RatingSeries.builder().id(1).userRating(4.0f).build();
        RatingSeries r2 = RatingSeries.builder().id(1).userRating(4.0f).build();

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testToStringContainsImportantFields() {
        RatingSeries rating = RatingSeries.builder()
                .id(15)
                .userRating(4.8f)
                .build();

        String toString = rating.toString();
        assertTrue(toString.contains("id=15"));
        assertTrue(toString.contains("userRating=4.8"));
    }
}
