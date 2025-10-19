package com.wam.lab1_maintenance.service;

import com.wam.lab1_maintenance.model.Series;
import com.wam.lab1_maintenance.repository.SeriesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service responsable de la gestion des séries tendance.
 * <p>
 * Cette classe permet d’analyser les séries existantes et de calculer les plus populaires
 * selon un algorithme basé sur leur note moyenne et leur nombre d’épisodes.
 */
@Service
@AllArgsConstructor
public class TrendingService {

    /** Référentiel d’accès aux données des séries. */
    private final SeriesRepository seriesRepository;

    /**
     * Calcule le classement des séries les plus tendances.
     * <p>
     * L’algorithme attribue à chaque série un score de popularité basé sur la racine carrée
     * du nombre d’épisodes multipliée par la note moyenne de la série.
     * Les 10 séries ayant le score le plus élevé sont retournées.
     *
     * @return une liste des 10 séries les plus populaires (ou moins s’il y en a moins dans la base)
     */
    public List<Series> topTendance() {

        List<Series> listSeries = getSeries();
        Map<Series, Double> scoresMap = new HashMap<>();

        // Calcul du score de tendance pour chaque série
        for (Series series : listSeries) {
            Float note = series.getNote();
            Integer vues = series.getNbEpisodes();

            if (note != null && vues != null && vues > 0) {
                double racineCarre = Math.sqrt(vues);
                double scoreTendance = racineCarre * note;
                scoresMap.put(series, scoreTendance);
            }
        }

        // Classement des séries par score décroissant
        List<Series> listTop10 = new ArrayList<>(scoresMap.keySet());
        listTop10.sort((s1, s2) -> Double.compare(scoresMap.get(s2), scoresMap.get(s1)));

        // Limite à 10 résultats maximum
        if (listTop10.size() > 10) {
            listTop10 = listTop10.subList(0, 10);
        }

        return listTop10;
    }

    /**
     * Récupère toutes les séries disponibles dans la base de données.
     *
     * @return la liste complète des séries
     */
    public List<Series> getSeries() {
        return seriesRepository.findAll();
    }
}
