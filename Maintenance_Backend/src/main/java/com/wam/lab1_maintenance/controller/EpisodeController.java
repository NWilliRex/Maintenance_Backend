package com.wam.lab1_maintenance.controller;

import com.wam.lab1_maintenance.model.Episode;
import com.wam.lab1_maintenance.service.EpisodeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Contrôleur REST responsable de la gestion des épisodes.
 * <p>
 * Fournit des points d’accès pour récupérer les informations des épisodes
 * disponibles dans la base de données ou dans une autre source configurée.
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class EpisodeController {

    private final EpisodeService episodeService;

    /**
     * Récupère la liste complète de tous les épisodes.
     *
     * @return une liste d’objets {@link Episode}
     * @throws FileNotFoundException si les données des épisodes ne peuvent pas être trouvées
     */
    @GetMapping("/episodes")
    public List<Episode> getAllEpisodes() throws FileNotFoundException {
        return episodeService.getAllEpisodes();
    }
}
