package com.wam.lab1_maintenance.service;

import com.wam.lab1_maintenance.model.RatingSeries;
import com.wam.lab1_maintenance.model.Series;
import com.wam.lab1_maintenance.model.User;
import com.wam.lab1_maintenance.repository.RatingSeriesRepository;
import com.wam.lab1_maintenance.repository.SeriesRepository;
import com.wam.lab1_maintenance.repository.UserRepository;
import com.wam.lab1_maintenance.request.RatingRequestBody;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;

/**
 * Service gérant la notation des séries par les utilisateurs.
 * <p>
 * Cette classe permet d’ajouter, de consulter et de modifier la note qu’un utilisateur attribue à une série.
 * Elle s’appuie sur les entités {@link User}, {@link Series} et {@link RatingSeries}.
 */
@Service
@AllArgsConstructor
public class RatingSerieService {

    /** Référentiel d’accès aux notations des séries. */
    private final RatingSeriesRepository ratingSeriesRepository;

    /** Référentiel d’accès aux utilisateurs. */
    private final UserRepository userRepository;

    /** Référentiel d’accès aux séries. */
    private final SeriesRepository seriesRepository;

    /**
     * Ajoute une nouvelle note donnée par un utilisateur pour une série.
     * <p>
     * Si l’utilisateur ou la série n’existent pas, une erreur {@link ResponseStatusException} avec code 404 est levée.
     * Si une note existe déjà pour cette série par cet utilisateur, une erreur 409 (CONFLICT) est lancée.
     *
     * @param userId identifiant unique de l’utilisateur
     * @param seriesId identifiant unique de la série
     * @param rating note attribuée par l’utilisateur (valeur flottante)
     * @throws ResponseStatusException en cas de données manquantes ou de duplication
     */
    public void addUserSeriesRating(long userId, long seriesId, float rating) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Erreur! Le user n'a pas été trouvé"));

        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Erreur! La série n'a pas été trouvée"));

        if (ratingSeriesRepository.findByUserIdAndSeriesId(userId, seriesId) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Erreur! Cette série a déjà reçue une note");
        }

        RatingSeries ratingsSeries = RatingSeries.builder()
                .series(series)
                .user(user)
                .userRating(rating)
                .ratedAt(LocalDateTime.now())
                .build();

        ratingSeriesRepository.save(ratingsSeries);
    }

    /**
     * Récupère la note donnée par un utilisateur pour une série spécifique.
     *
     * @param userId identifiant unique de l’utilisateur
     * @param seriesId identifiant unique de la série
     * @return la note attribuée par l’utilisateur, ou {@code null} si aucune note n’a été trouvée
     */
    public Float getUserSeriesRating(long userId, long seriesId) {
        RatingSeries ratingsSeries = ratingSeriesRepository.findByUserIdAndSeriesId(userId, seriesId);
        return ratingsSeries.getUserRating();
    }

    /**
     * Récupère la note moyenne ou globale d’une série.
     *
     * @param id identifiant unique de la série
     * @return la note de la série
     * @throws EntityNotFoundException si la série n’existe pas
     */
    public Float getSeriesRating(long id) {
        Series series = seriesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Episode not found"));
        return series.getNote();
    }

    /**
     * Met à jour la note donnée par un utilisateur pour une série.
     * <p>
     * Si la nouvelle note est absente dans {@link RatingRequestBody}, la note précédente est conservée.
     * La date de modification est automatiquement mise à jour.
     *
     * @param userId identifiant unique de l’utilisateur
     * @param seriesId identifiant unique de la série
     * @param body corps de la requête contenant la nouvelle note
     * @return l’objet {@link RatingSeries} mis à jour
     */
    public RatingSeries updateUserSeriesRating(long userId, long seriesId, RatingRequestBody body) {
        RatingSeries ratingsSeries = ratingSeriesRepository.findByUserIdAndSeriesId(userId, seriesId);

        RatingSeries updateRatingsSeries = RatingSeries.builder()
                .id(ratingsSeries.getId())
                .user(ratingsSeries.getUser())
                .series(ratingsSeries.getSeries())
                .userRating(body.userRating() == null ? ratingsSeries.getUserRating() : body.userRating())
                .ratedAt(LocalDateTime.now())
                .build();

        return ratingSeriesRepository.save(updateRatingsSeries);
    }
}
