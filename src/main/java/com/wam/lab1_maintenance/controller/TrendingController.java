package com.wam.lab1_maintenance.controller;

import com.wam.lab1_maintenance.model.Series;
import com.wam.lab1_maintenance.service.TrendingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur REST responsable de la récupération des séries les plus tendances.
 * <p>
 * Cet endpoint permet de consulter les séries les plus populaires ou les plus regardées
 * selon la logique définie dans {@link TrendingService}.
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TrendingController {

    private final TrendingService trendingService;

    /**
     * Récupère la liste des séries en tendance.
     * <p>
     * L’ordre et la sélection des séries dépendent de la logique du service
     * (par exemple, nombre de vues, notes moyennes, ou activité récente des utilisateurs).
     *
     * @return une liste de séries considérées comme les plus populaires du moment
     */
    @GetMapping("/series/trending")
    public List<Series> getTrendingSeries() {
        return trendingService.topTendance();
    }
}
