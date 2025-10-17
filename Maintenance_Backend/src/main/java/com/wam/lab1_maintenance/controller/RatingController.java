package com.wam.lab1_maintenance.controller;

import com.wam.lab1_maintenance.model.RatingSeries;
import com.wam.lab1_maintenance.request.RatingRequestBody;
import com.wam.lab1_maintenance.service.RatingSerieService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST gérant les évaluations (notes) des séries par les utilisateurs.
 * <p>
 * Ce contrôleur permet d’ajouter, consulter et mettre à jour les notes attribuées
 * par les utilisateurs aux séries.
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RatingController {

    private final RatingSerieService ratingsService;

    /**
     * Ajoute une nouvelle note donnée par un utilisateur à une série.
     *
     * @param userId     identifiant unique de l’utilisateur
     * @param seriesId   identifiant unique de la série
     * @param ratingBody note envoyée dans le corps de la requête (au format texte)
     */
    @PostMapping(
            value = "/ratings/users/{userId}/series/{seriesId}",
            consumes = MediaType.TEXT_PLAIN_VALUE
    )
    public void addUserSeriesRating(
            @PathVariable Long userId,
            @PathVariable Long seriesId,
            @RequestBody String ratingBody
    ) {
        Float rating = Float.valueOf(ratingBody.trim());
        ratingsService.addUserSeriesRating(userId, seriesId, rating);
    }

    /**
     * Récupère la note attribuée par un utilisateur spécifique à une série donnée.
     *
     * @param userId   identifiant unique de l’utilisateur
     * @param seriesId identifiant unique de la série
     * @return la note attribuée, ou {@code null} si aucune note n’existe
     */
    @GetMapping("/ratings/users/{userId}/series/{seriesId}")
    public Float getUserSeriesRating(
            @PathVariable Long userId,
            @PathVariable Long seriesId
    ) {
        return ratingsService.getUserSeriesRating(userId, seriesId);
    }

    /**
     * Récupère la note moyenne d’une série donnée.
     *
     * @param id identifiant unique de la série
     * @return la moyenne des notes attribuées à cette série
     */
    @GetMapping("/ratings/series/{id}")
    public Float getSeriesRating(@PathVariable Long id) {
        return ratingsService.getSeriesRating(id);
    }

    /**
     * Met à jour la note d’un utilisateur pour une série donnée.
     *
     * @param userId   identifiant unique de l’utilisateur
     * @param seriesId identifiant unique de la série
     * @param body     nouvel objet contenant la note mise à jour
     * @return l’objet {@link RatingSeries} mis à jour
     */
    @PutMapping("/ratings/users/{userId}/series/{seriesId}")
    public RatingSeries updateUserSeriesRating(
            @PathVariable Long userId,
            @PathVariable Long seriesId,
            @RequestBody RatingRequestBody body
    ) {
        return ratingsService.updateUserSeriesRating(userId, seriesId, body);
    }
}
