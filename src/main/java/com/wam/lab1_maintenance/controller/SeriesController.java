package com.wam.lab1_maintenance.controller;

import com.wam.lab1_maintenance.model.Series;
import com.wam.lab1_maintenance.request.SeriesRequestBody;
import com.wam.lab1_maintenance.service.SerieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST responsable de la gestion des séries dans l'application.
 * <p>
 * Il permet de rechercher, d'ajouter, de modifier, de consulter et de supprimer des séries
 * via des endpoints REST.
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class SeriesController {

    private final SerieService seriesService;

    /**
     * Recherche des séries en fonction de critères optionnels tels que le genre, le titre ou le nombre d'épisodes.
     *
     * @param genre       le genre de la série (facultatif)
     * @param title       le titre ou une partie du titre de la série (facultatif)
     * @param nbEpisodes  le nombre d'épisodes exact recherché (facultatif)
     * @return une liste de séries correspondant aux critères spécifiés
     */
    @GetMapping("/search")
    public List<Series> searchSeries(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer nbEpisodes) {
        return seriesService.searchSeriesPersonalised(genre, title, nbEpisodes);
    }

    /**
     * Récupère la liste complète de toutes les séries disponibles.
     *
     * @return une liste contenant toutes les séries enregistrées
     */
    @GetMapping("/series")
    public List<Series> getAllSeries() {
        return seriesService.getAllSeries();
    }

    /**
     * Récupère les détails d'une série spécifique selon son identifiant.
     *
     * @param id identifiant unique de la série
     * @return l'objet {@link Series} correspondant à l'identifiant fourni
     */
    @GetMapping("/series/{id}")
    public Series getSeriesDetails(@PathVariable Long id) {
        return seriesService.getSerieById(id);
    }

    /**
     * Ajoute une nouvelle série à la base de données.
     *
     * @param body le corps de la requête contenant les informations de la série à ajouter
     * @return la série nouvellement ajoutée
     */
    @PostMapping("/addSeries")
    public Series addSeries(@RequestBody SeriesRequestBody body) {
        return seriesService.addSeries(body);
    }

    /**
     * Met à jour une série existante selon son identifiant.
     *
     * @param id   identifiant de la série à mettre à jour
     * @param body le corps de la requête contenant les nouvelles informations
     * @return la série mise à jour
     */
    @PutMapping("/updateSeries/{id}")
    public Series updateSeries(@PathVariable Long id, @RequestBody SeriesRequestBody body) {
        return seriesService.updateSeries(id, body);
    }

    /**
     * Supprime une série de la base de données.
     *
     * @param id identifiant unique de la série à supprimer
     */
    @DeleteMapping("/deleteSeries/{id}")
    public void deleteSeries(@PathVariable Long id) {
        seriesService.deleteSeries(id);
    }
}
