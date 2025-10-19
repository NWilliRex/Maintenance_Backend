package com.wam.lab1_maintenance.service;

import com.wam.lab1_maintenance.model.Episode;
import com.wam.lab1_maintenance.model.History;
import com.wam.lab1_maintenance.model.User;
import com.wam.lab1_maintenance.repository.EpisodeRepository;
import com.wam.lab1_maintenance.repository.HistoryRepository;
import com.wam.lab1_maintenance.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service gérant l’historique de visionnage des utilisateurs.
 * <p>
 * Cette classe permet de consulter et d’ajouter des épisodes dans l’historique
 * d’un utilisateur à partir des entités {@link User}, {@link Episode} et {@link History}.
 */
@Service
@AllArgsConstructor
public class HistoryService {

    /** Référentiel pour accéder aux données des utilisateurs. */
    private final UserRepository userRepository;

    /** Référentiel pour accéder aux données des épisodes. */
    private final EpisodeRepository episodeRepository;

    /** Référentiel pour accéder aux historiques de visionnage. */
    private final HistoryRepository historyRepository;

    /**
     * Récupère la liste des épisodes visionnés par un utilisateur donné.
     *
     * @param userId identifiant unique de l’utilisateur
     * @return une liste d’objets {@link Episode} correspondant à l’historique du user
     */
    public List<Episode> searchHistoryPerson(Long userId) {
        return historyRepository.findByUser_Id(userId).stream()
                .map(History::getEpisode)
                .toList();
    }

    /**
     * Ajoute un épisode dans l’historique d’un utilisateur.
     * <p>
     * Si l’utilisateur ou l’épisode n’existent pas, une exception {@link EntityNotFoundException} est levée.
     *
     * @param userId identifiant unique de l’utilisateur
     * @param episodeId identifiant unique de l’épisode à ajouter
     * @throws EntityNotFoundException si l’utilisateur ou l’épisode n’est pas trouvé
     */
    public void addEpisode(Long userId, Long episodeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Erreur! Le user n'a pas été trouvé"));

        Episode episode = episodeRepository.findById(episodeId)
                .orElseThrow(() -> new EntityNotFoundException("Erreur! L'épisode n'a pas été trouvé"));

        History history = History.builder()
                .episode(episode)
                .user(user)
                .build();

        historyRepository.save(history);
    }
}
