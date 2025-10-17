package com.wam.lab1_maintenance.service;

import com.wam.lab1_maintenance.model.Episode;
import com.wam.lab1_maintenance.repository.EpisodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsable de la gestion des épisodes.
 * <p>
 * Cette classe interagit avec le {@link EpisodeRepository} pour récupérer les données
 * liées aux épisodes depuis la base de données.
 */
@Service
@AllArgsConstructor
public class EpisodeService {

    /** Référentiel permettant l’accès aux données des épisodes. */
    private final EpisodeRepository episodeRepository;

    /**
     * Récupère la liste complète de tous les épisodes enregistrés dans la base de données.
     *
     * @return une liste de tous les {@link Episode} disponibles
     */
    public List<Episode> getAllEpisodes() {
        return episodeRepository.findAll();
    }
}
