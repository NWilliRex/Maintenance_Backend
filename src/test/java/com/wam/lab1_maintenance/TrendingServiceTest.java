package com.wam.lab1_maintenance;

import com.wam.lab1_maintenance.model.Series;
import com.wam.lab1_maintenance.repository.SeriesRepository;
import com.wam.lab1_maintenance.service.TrendingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrendingServiceTest {

    @Mock
    private SeriesRepository seriesRepository;

    @InjectMocks
    private TrendingService trendingService;

    private List<Series> mockSeriesList;

    @BeforeEach
    void setUp() {
        mockSeriesList = List.of(
                Series.builder().id(1L).title("Attack on Titan").nbEpisodes(25).note(9.5f).build(),
                Series.builder().id(2L).title("Naruto").nbEpisodes(220).note(8.0f).build(),
                Series.builder().id(3L).title("One Piece").nbEpisodes(1000).note(9.0f).build(),
                Series.builder().id(4L).title("Bleach").nbEpisodes(366).note(7.5f).build(),
                Series.builder().id(5L).title("Chainsaw Man").nbEpisodes(12).note(9.0f).build()
        );
    }

    // ✅ Basic test - ensures sorting by calculated score
    @Test
    void testTopTendance_ShouldReturnSeriesSortedByScore() {
        when(seriesRepository.findAll()).thenReturn(mockSeriesList);

        List<Series> result = trendingService.topTendance();

        assertEquals(5, result.size());
        assertEquals("One Piece", result.get(0).getTitle(), "One Piece should have the highest score");
        assertTrue(result.get(0).getTitle().equals("One Piece") || result.get(0).getTitle().equals("Attack on Titan"));
        verify(seriesRepository, times(1)).findAll();
    }

    // ✅ Handles null note and nbEpisodes properly (ignored in score)
    @Test
    void testTopTendance_ShouldIgnoreNullValues() {
        List<Series> invalidSeries = List.of(
                Series.builder().id(6L).title("Invalid1").nbEpisodes(null).note(8.0f).build(),
                Series.builder().id(7L).title("Invalid2").nbEpisodes(10).note(null).build(),
                Series.builder().id(8L).title("Invalid3").nbEpisodes(0).note(8.0f).build()
        );

        List<Series> allSeries = new ArrayList<>(mockSeriesList);
        allSeries.addAll(invalidSeries);

        when(seriesRepository.findAll()).thenReturn(allSeries);

        List<Series> result = trendingService.topTendance();

        assertEquals(mockSeriesList.size(), result.size(), "Invalid series should not be included");
        assertFalse(result.stream().anyMatch(s -> s.getTitle().startsWith("Invalid")));
    }

    // ✅ When repository returns less than 10 series
    @Test
    void testTopTendance_WhenLessThan10Series_ShouldReturnAll() {
        when(seriesRepository.findAll()).thenReturn(mockSeriesList);

        List<Series> result = trendingService.topTendance();

        assertEquals(mockSeriesList.size(), result.size());
        assertTrue(result.size() <= 10);
    }

    // ✅ When more than 10 series, should return exactly 10
    @Test
    void testTopTendance_WhenMoreThan10Series_ShouldReturnTop10() {
        List<Series> longList = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            longList.add(Series.builder()
                    .id((long) i)
                    .title("Series" + i)
                    .nbEpisodes(10 * i)
                    .note(5.0f + i % 5)
                    .build());
        }

        when(seriesRepository.findAll()).thenReturn(longList);

        List<Series> result = trendingService.topTendance();

        assertEquals(10, result.size());
        verify(seriesRepository, times(1)).findAll();
    }

    // ✅ getSeries() should call repository
    @Test
    void testGetSeries_ShouldReturnAllFromRepository() {
        when(seriesRepository.findAll()).thenReturn(mockSeriesList);

        List<Series> result = trendingService.getSeries();

        assertEquals(mockSeriesList, result);
        verify(seriesRepository, times(1)).findAll();
    }

    // ✅ Empty repository case
    @Test
    void testTopTendance_WhenNoSeries_ShouldReturnEmptyList() {
        when(seriesRepository.findAll()).thenReturn(Collections.emptyList());

        List<Series> result = trendingService.topTendance();

        assertTrue(result.isEmpty());
        verify(seriesRepository, times(1)).findAll();
    }
}
