package com.wam.lab1_maintenance;

import com.wam.lab1_maintenance.model.Episode;
import com.wam.lab1_maintenance.model.History;
import com.wam.lab1_maintenance.model.Series;
import com.wam.lab1_maintenance.repository.HistoryRepository;
import com.wam.lab1_maintenance.repository.SeriesRepository;
import com.wam.lab1_maintenance.service.RecommendationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RecommendationServiceTest {

    @Mock
    private HistoryRepository historyRepository;

    @Mock
    private SeriesRepository seriesRepository;

    @InjectMocks
    private RecommendationService recommendationService;

    private Series actionSeries;
    private Series comedySeries;
    private Series dramaSeries;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        actionSeries = Series.builder().id(1L).title("Action Show").genre("Action").build();
        comedySeries = Series.builder().id(2L).title("Comedy Show").genre("Comedy").build();
        dramaSeries = Series.builder().id(3L).title("Drama Show").genre("Drama").build();
    }

    @Test
    void testRecommendationForUser_ReturnsRecommendationsBasedOnTopGenres() {
        // 🧩 Given: history contains multiple episodes from different genres
        History history1 = History.builder()
                .episode(Episode.builder().serie(actionSeries).build())
                .build();

        History history2 = History.builder()
                .episode(Episode.builder().serie(actionSeries).build())
                .build();

        History history3 = History.builder()
                .episode(Episode.builder().serie(comedySeries).build())
                .build();

        when(historyRepository.findAll()).thenReturn(List.of(history1, history2, history3));

        // For each genre, define what series are available
        when(seriesRepository.findByGenre("Action")).thenReturn(List.of(actionSeries));
        when(seriesRepository.findByGenre("Comedy")).thenReturn(List.of(comedySeries));

        // 🎯 When
        Map<String, List<Series>> recommendations = recommendationService.recommendationForUser(1L);

        // ✅ Then
        assertEquals(2, recommendations.size());
        assertEquals(List.of(actionSeries), recommendations.get("Action"));
        assertEquals(List.of(comedySeries), recommendations.get("Comedy"));
    }

    @Test
    void testRecommendationForUser_NoHistory_ReturnsEmptyMap() {
        when(historyRepository.findAll()).thenReturn(List.of());

        Map<String, List<Series>> recommendations = recommendationService.recommendationForUser(1L);

        assertEquals(0, recommendations.size());
    }

    @Test
    void testRecommendationForUser_LimitsToTop3GenresAnd3Series() {
        // 🧩 Given: 4 genres in history (only 3 should be kept)
        Series genreA = Series.builder().id(10L).title("A").genre("A").build();
        Series genreB = Series.builder().id(11L).title("B").genre("B").build();
        Series genreC = Series.builder().id(12L).title("C").genre("C").build();
        Series genreD = Series.builder().id(13L).title("D").genre("D").build();

        History hA = History.builder().episode(Episode.builder().serie(genreA).build()).build();
        History hB1 = History.builder().episode(Episode.builder().serie(genreB).build()).build();
        History hB2 = History.builder().episode(Episode.builder().serie(genreB).build()).build();
        History hC1 = History.builder().episode(Episode.builder().serie(genreC).build()).build();
        History hC2 = History.builder().episode(Episode.builder().serie(genreC).build()).build();
        History hC3 = History.builder().episode(Episode.builder().serie(genreC).build()).build();

        when(historyRepository.findAll()).thenReturn(List.of(hA, hB1, hB2, hC1, hC2, hC3));

        when(seriesRepository.findByGenre("C")).thenReturn(List.of(genreC, genreC, genreC, genreC)); // 4 → limited to 3
        when(seriesRepository.findByGenre("B")).thenReturn(List.of(genreB));
        when(seriesRepository.findByGenre("A")).thenReturn(List.of(genreA));

        // 🎯 When
        Map<String, List<Series>> recommendations = recommendationService.recommendationForUser(5L);

        // ✅ Then
        assertEquals(3, recommendations.size());
        assertEquals(3, recommendations.get("C").size());
    }
}
