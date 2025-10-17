package com.wam.lab1_maintenance;

import com.wam.lab1_maintenance.config.DataSeeder;
import com.wam.lab1_maintenance.model.*;
import com.wam.lab1_maintenance.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link DataSeeder}.
 */
class DataSeederTest {

    @Mock private SeriesRepository seriesRepo;
    @Mock private EpisodeRepository episodeRepo;
    @Mock private UserRepository userRepo;
    @Mock private RatingSeriesRepository ratingRepo;
    @Mock private HistoryRepository historyRepo;

    @InjectMocks private DataSeeder dataSeeder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --- run() logic ---

    @Test
    void testRun_NoExistingData_CallsSeedingMethods() {
        when(seriesRepo.count()).thenReturn(0L);
        when(userRepo.count()).thenReturn(0L);

        // Use spies to intercept internal calls
        DataSeeder spySeeder = Mockito.spy(dataSeeder);
        doNothing().when(spySeeder).seedSeries();
        doNothing().when(spySeeder).seedUsers();
        doNothing().when(spySeeder).seedRatingsAndHistory();

        spySeeder.run();

        verify(spySeeder, times(1)).seedSeries();
        verify(spySeeder, times(1)).seedUsers();
        verify(spySeeder, times(1)).seedRatingsAndHistory();
    }

    @Test
    void testRun_ExistingData_SkipsSeeding() {
        when(seriesRepo.count()).thenReturn(10L);
        when(userRepo.count()).thenReturn(5L);

        DataSeeder spySeeder = Mockito.spy(dataSeeder);
        spySeeder.run();

        verify(spySeeder, never()).seedSeries();
        verify(spySeeder, never()).seedUsers();
        verify(spySeeder, never()).seedRatingsAndHistory();
    }

    // --- seedSeries() logic ---

    @Test
    void testSeedSeries_SavesSeriesAndEpisodes() {
        when(seriesRepo.save(any(Series.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(episodeRepo.save(any(Episode.class))).thenAnswer(invocation -> invocation.getArgument(0));

        dataSeeder.seedSeries();

        verify(seriesRepo, atLeastOnce()).save(any(Series.class));
        verify(episodeRepo, atLeastOnce()).save(any(Episode.class));
    }

    // --- seedUsers() logic ---

    @Test
    void testSeedUsers_Creates25Users_AndEncodesPasswords() {
        when(userRepo.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        dataSeeder.seedUsers();

        verify(userRepo, times(25)).save(any(User.class));
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepo, atLeastOnce()).save(captor.capture());

        User firstUser = captor.getAllValues().get(0);
        assertNotNull(firstUser.getPassword());
        assertTrue(firstUser.getPassword().startsWith("$2a$")); // BCrypt hash prefix
    }

    // --- seedRatingsAndHistory() logic ---

    @Test
    void testSeedRatingsAndHistory_GeneratesRatingsAndHistory() {
        User user = new User();
        user.setUsername("user1");

        Series series = new Series();
        series.setTitle("Test Series");

        Episode episode = new Episode();
        episode.setTitle("Episode 1");

        when(userRepo.findAll()).thenReturn(Collections.singletonList(user));
        when(seriesRepo.findAll()).thenReturn(Collections.singletonList(series));
        when(episodeRepo.findAll()).thenReturn(Collections.singletonList(episode));

        dataSeeder.seedRatingsAndHistory();

        verify(ratingRepo, atLeastOnce()).save(any(RatingSeries.class));
        verify(historyRepo, atLeastOnce()).save(any(History.class));
    }

    // --- randomDateWithinLastMonths() ---

    @Test
    void testRandomDateWithinLastMonths_ReturnsPastDate() {
        LocalDateTime date = invokeRandomDate();
        assertNotNull(date);
        assertTrue(date.isBefore(LocalDateTime.now()) || date.isEqual(LocalDateTime.now()));
    }

    // --- randomGenre() ---

    @Test
    void testRandomGenre_ReturnsValidGenre() {
        String genre = invokeRandomGenre();
        List<String> validGenres = Arrays.asList("Crime", "Action", "Animation", "Drama", "Sci-Fi", "Fantasy");
        assertTrue(validGenres.contains(genre));
    }

    // --- Helper reflection methods for private functions ---

    private LocalDateTime invokeRandomDate() {
        try {
            var method = DataSeeder.class.getDeclaredMethod("randomDateWithinLastMonths");
            method.setAccessible(true);
            return (LocalDateTime) method.invoke(dataSeeder);
        } catch (Exception e) {
            fail("Could not invoke randomDateWithinLastMonths: " + e.getMessage());
            return null;
        }
    }

    private String invokeRandomGenre() {
        try {
            var method = DataSeeder.class.getDeclaredMethod("randomGenre");
            method.setAccessible(true);
            return (String) method.invoke(dataSeeder);
        } catch (Exception e) {
            fail("Could not invoke randomGenre: " + e.getMessage());
            return null;
        }
    }
}
