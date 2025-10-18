package com.wam.lab1_maintenance.service;

import com.wam.lab1_maintenance.model.Series;
import com.wam.lab1_maintenance.repository.SeriesRepository;
import com.wam.lab1_maintenance.request.SeriesRequestBody;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsable de la gestion des séries.
 * <p>
 * Cette classe gère toutes les opérations CRUD relatives aux entités {@link Series},
 * incluant la création, la modification, la suppression et la recherche.
 */
@Service
@AllArgsConstructor
public class SerieService {

    /** Référentiel d’accès aux données des séries. */
    private final SeriesRepository seriesRepository;

    /**
     * Recherche des séries selon différents critères facultatifs.
     * <p>
     * Si un critère est nul ou vide, une méthode de recherche spécifique est utilisée.
     * En l’absence de critères valides, toutes les séries sont retournées.
     *
     * @param genre      le genre recherché (ex. : "Action", "Comédie")
     * @param title      le titre ou préfixe de titre à rechercher
     * @param nbEpisode  le nombre minimum d’épisodes souhaité
     * @return une liste de séries correspondant aux critères
     */
    public List<Series> searchSeriesPersonalised(String genre, String title, Integer nbEpisode) {
        if (genre == null || genre.isEmpty()) {
            return seriesRepository.findByGenre(genre);
        } else if (title == null || title.isEmpty()) {
            return seriesRepository.findByTitleStartingWith(title);
        } else if (nbEpisode == null || nbEpisode < 0) {
            return seriesRepository.findByNbEpisodesGreaterThanEqual(nbEpisode);
        } else {
            return seriesRepository.findAll();
        }
    }

    /**
     * Récupère la liste complète des séries disponibles.
     *
     * @return la liste de toutes les séries dans la base de données
     */
    public List<Series> getAllSeries() {
        return seriesRepository.findAll();
    }

    /**
     * Recherche une série spécifique selon son identifiant.
     *
     * @param id identifiant unique de la série
     * @return la série correspondante
     * @throws EntityNotFoundException si aucune série ne correspond à l’identifiant fourni
     */
    public Series getSerieById(Long id) {
        return seriesRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Ajoute une nouvelle série à la base de données.
     *
     * @param body objet {@link SeriesRequestBody} contenant les informations de la série
     * @return la série nouvellement créée
     */
    public Series addSeries(@org.jetbrains.annotations.NotNull SeriesRequestBody body) {
        Series series = Series.builder()
                .title(body.title())
                .genre(body.genre())
                .nbEpisodes(body.nbEpisodes())
                .note(body.note())
                .build();
        seriesRepository.save(series);
        return series;
    }

    /**
     * Met à jour une série existante.
     * <p>
     * Seules les propriétés non nulles dans {@code body} seront modifiées.
     *
     * @param id   identifiant de la série à modifier
     * @param body données à mettre à jour
     * @return la série mise à jour
     * @throws EntityNotFoundException si la série n’existe pas
     */
    public Series updateSeries(Long id, SeriesRequestBody body) {
        Series series = seriesRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        Series updateSeries = Series.builder()
                .id(series.getId())
                .title(body.title() == null ? series.getTitle() : body.title())
                .genre(body.genre() == null ? series.getGenre() : body.genre())
                .nbEpisodes(body.nbEpisodes() == null ? series.getNbEpisodes() : body.nbEpisodes())
                .note(body.note() == null ? series.getNote() : body.note())
                .build();
        return seriesRepository.save(updateSeries);
    }

    /**
     * Supprime une série existante de la base de données.
     *
     * @param id identifiant unique de la série à supprimer
     */
    public void deleteSeries(Long id) {
        seriesRepository.deleteById(id);
    }
}
