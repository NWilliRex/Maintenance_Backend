package com.wam.lab1_maintenance.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Représente l'historique de visionnage d'un utilisateur.
 * <p>
 * Chaque enregistrement lie un {@link User} à un {@link Episode}
 * et enregistre le moment exact où l’épisode a été visionné.
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class History {

    /**
     * Identifiant unique de l'entrée d'historique.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    /**
     * Épisode visionné par l'utilisateur.
     * Relation {@code ManyToOne} avec {@link Episode}.
     */
    @ManyToOne
    @JoinColumn(name = "episode_id")
    private Episode episode;

    /**
     * Utilisateur ayant visionné l'épisode.
     * Relation {@code ManyToOne} avec {@link User}.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Date et heure du visionnage.
     */
    @Column(name = "watched_at")
    private LocalDateTime watchedAt;
}
