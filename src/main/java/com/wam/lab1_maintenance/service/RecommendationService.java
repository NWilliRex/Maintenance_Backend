package com.wam.lab1_maintenance.service;

import com.wam.lab1_maintenance.model.Series;
import com.wam.lab1_maintenance.repository.HistoryRepository;
import com.wam.lab1_maintenance.repository.SeriesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecommendationService {
    private final HistoryRepository historyRepository;
    private final SeriesRepository seriesRepository;
    private static final Long nbrRecommendationGenre = 3L;
    private static final Long nbrRecommendationSeries = 3L;

    public Map<String, List<Series>> recommendationForUser(Long userId) {
        Map<String, List<Series>> result = new HashMap<>();

        List<String> topGenres = findTopGenresForUser();

        topGenres.forEach(genre -> {
            List<Series> seriesByGenre = findSeriesByGenre(genre);
            result.put(genre, seriesByGenre);
        });

        return result;
    }

    // Utilisation d'un stream pour faire toutes les transformations pour retourne liste de serie
    private List<String> findTopGenresForUser() {
        return historyRepository.findAll().stream()
                .collect(Collectors.groupingBy(e -> e.getEpisode().getSerie().getGenre(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(nbrRecommendationGenre)
                .map(Map.Entry::getKey)
                .toList();
    }

    private List<Series> findSeriesByGenre(String genre) {
        return seriesRepository.findByGenre(genre).stream()
                .limit(nbrRecommendationSeries)
                .toList();
    }
}
