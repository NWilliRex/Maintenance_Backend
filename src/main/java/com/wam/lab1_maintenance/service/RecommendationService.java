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

/**
 * Service responsable de la recommandation de séries aux utilisateurs.
 * <p>
 * Cette classe utilise l’historique des épisodes visionnés par un utilisateur
 * pour déterminer ses genres préférés, puis recommande un ensemble limité
 * de séries similaires basées sur ces genres.
 */
@Service
@AllArgsConstructor
public class RecommendationService {

    /** Référentiel d’accès aux historiques de visionnage. */
    private final HistoryRepository historyRepository;

    /** Référentiel d’accès aux séries. */
    private final SeriesRepository seriesRepository;

    /** Nombre maximum de genres à recommander. */
    private static final Long nbrRecommendationGenre = 3L;

    /** Nombre maximum de séries à recommander par genre. */
    private static final Long nbrRecommendationSeries = 3L;

    /**
     * Génère des recommandations personnalisées pour un utilisateur donné.
     * <p>
     * Le système identifie d’abord les genres les plus regardés par l’utilisateur,
     * puis sélectionne plusieurs séries correspondant à ces genres.
     *
     * @param userId identifiant unique de l’utilisateur
     * @return une {@link Map} associant chaque genre préféré à une liste de séries recommandées
     */
    public Map<String, List<Series>> recommendationForUser(Long userId) {
        Map<String, List<Series>> result = new HashMap<>();

        List<String> topGenres = findTopGenresForUser();

        topGenres.forEach(genre -> {
            List<Series> seriesByGenre = findSeriesByGenre(genre);
            result.put(genre, seriesByGenre);
        });

        return result;
    }

    /**
     * Détermine les genres les plus regardés par l’ensemble des utilisateurs.
     * <p>
     * Actuellement, cette méthode ne filtre pas par utilisateur
     * (elle analyse tout l’historique global). Pour un comportement plus précis,
     * il serait nécessaire de filtrer les historiques par {@code userId}.
     *
     * @return une liste triée des genres les plus populaires, limitée à {@code nbrRecommendationGenre}
     */
    private List<String> findTopGenresForUser() {
        return historyRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        e -> e.getEpisode().getSerie().getGenre(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(nbrRecommendationGenre)
                .map(Map.Entry::getKey)
                .toList();
    }

    /**
     * Récupère un nombre limité de séries correspondant à un genre donné.
     *
     * @param genre le genre recherché (ex. : "Drame", "Comédie", "Action")
     * @return une liste de séries appartenant à ce genre, limitée à {@code nbrRecommendationSeries}
     */
    private List<Series> findSeriesByGenre(String genre) {
        return seriesRepository.findByGenre(genre).stream()
                .limit(nbrRecommendationSeries)
                .toList();
    }
}
