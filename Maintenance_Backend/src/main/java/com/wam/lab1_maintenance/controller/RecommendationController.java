package com.wam.lab1_maintenance.controller;

import com.wam.lab1_maintenance.model.Series;
import com.wam.lab1_maintenance.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Contrôleur REST responsable de la gestion des recommandations de séries pour les utilisateurs.
 * <p>
 * Ce contrôleur permet d’obtenir des suggestions personnalisées basées sur
 * les préférences et l’historique de visionnage de l’utilisateur.
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    /**
     * Récupère les recommandations de séries pour un utilisateur spécifique.
     *
     * @param id identifiant unique de l’utilisateur
     * @return une map contenant des catégories de recommandations (ex : "Populaires", "Basées sur vos goûts")
     *         associées à leurs listes de séries
     */
    @GetMapping("/users/{id}/recommendations")
    public Map<String, List<Series>> getRecommendations(@PathVariable("id") Long id) {
        return recommendationService.recommendationForUser(id);
    }
}
