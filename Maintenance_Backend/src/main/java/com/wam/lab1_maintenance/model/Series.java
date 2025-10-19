package com.wam.lab1_maintenance.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente une série télévisée.
 * <p>
 * Cette entité contient les informations principales d'une série,
 * notamment son titre, son genre, le nombre d’épisodes et sa note moyenne.
 */
@Entity
@Table(name = "serie")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Series {

    /**
     * Identifiant unique de la série.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serie_id")
    private Long id;

    /**
     * Titre de la série.
     */
    @Column(name = "title")
    private String title;

    /**
     * Genre principal de la série (ex. : drame, comédie, science-fiction, etc.).
     */
    @Column(name = "genre")
    private String genre;

    /**
     * Nombre total d’épisodes dans la série.
     */
    @Column(name = "nb_episodes")
    private Integer nbEpisodes;

    /**
     * Note moyenne attribuée à la série (basée sur les évaluations des utilisateurs).
     */
    @Column(name = "note")
    private Float note;

    /**
     * Description de la série.
     * <p>
     * Champ non persisté en base de données, utilisé seulement à des fins d’affichage ou de logique applicative.
     */
    @Transient
    private String description;
}
