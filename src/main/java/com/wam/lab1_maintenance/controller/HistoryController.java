package com.wam.lab1_maintenance.controller;

import com.wam.lab1_maintenance.model.Episode;
import com.wam.lab1_maintenance.service.HistoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST gérant l’historique de visionnage des utilisateurs.
 * <p>
 * Ce contrôleur permet de récupérer l’historique d’un utilisateur
 * et d’ajouter un épisode à son historique.
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    /**
     * Récupère la liste des épisodes visionnés par un utilisateur donné.
     *
     * @param userId identifiant unique de l’utilisateur
     * @return une liste d’objets {@link Episode} représentant l’historique de visionnage
     */
    @GetMapping("/user/{userId}/history")
    public List<Episode> historyForUser(@PathVariable("userId") Long userId) {
        return historyService.searchHistoryPerson(userId);
    }

    /**
     * Ajoute un épisode spécifique à l’historique d’un utilisateur.
     *
     * @param userId    identifiant unique de l’utilisateur
     * @param episodeId identifiant unique de l’épisode à ajouter
     */
    @PostMapping("/users/{id}/history/{episodeId}")
    public void addEpisodeToUser(@PathVariable("id") Long userId, @PathVariable("episodeId") Long episodeId) {
        historyService.addEpisode(userId, episodeId);
    }
}
