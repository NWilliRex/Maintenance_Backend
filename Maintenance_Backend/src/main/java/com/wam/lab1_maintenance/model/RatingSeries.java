package com.wam.lab1_maintenance.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Représente une évaluation donnée par un utilisateur pour une série spécifique.
 * <p>
 * Cette entité enregistre la note attribuée, la série concernée,
 * l'utilisateur ayant effectué l'évaluation ainsi que la date de notation.
 */
@Entity
@Table(name = "ratings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingSeries {

    /**
     * Identifiant unique de l’évaluation.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ratings_id")
    private int id;

    /**
     * Utilisateur ayant noté la série.
     * Relation {@code ManyToOne} avec {@link User}.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Série évaluée par l’utilisateur.
     * Relation {@code ManyToOne} avec {@link Series}.
     */
    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Series series;

    /**
     * Note donnée par l’utilisateur.
     * Peut être une valeur décimale (ex. 4.5).
     */
    @Column(name = "user_rating")
    private Float userRating;

    /**
     * Date et heure auxquelles la note a été attribuée.
     */
    @Column(name = "rated_at")
    private LocalDateTime ratedAt;

    /**
     * Définit la note de l’utilisateur sous forme d’entier.
     *
     * @param rating la note à enregistrer (convertie automatiquement en float)
     */
    public void setRating(int rating) {
        this.userRating = (float) rating;
    }

    /**
     * Définit la note de l’utilisateur sous forme de flottant.
     *
     * @param rating la note à enregistrer
     */
    public void setRating(float rating) {
        this.userRating = rating;
    }
}
