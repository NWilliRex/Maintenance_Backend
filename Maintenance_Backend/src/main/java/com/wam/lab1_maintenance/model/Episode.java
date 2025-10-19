package com.wam.lab1_maintenance.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente un épisode appartenant à une série télévisée.
 * <p>
 * Chaque épisode possède un titre, un numéro d’épisode, une note,
 * et une référence à la série {@link Series} à laquelle il appartient.
 */
@Entity
@Table(name = "episode")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Episode {

    /**
     * Identifiant unique de l’épisode.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "episode_id")
    private Long id;

    /**
     * Titre de l’épisode.
     */
    @Column(name = "title")
    private String title;

    /**
     * Numéro de l’épisode dans la série.
     * Ce champ est obligatoire.
     */
    @Column(name = "episode_number", nullable = false)
    private Integer episodeNumber;

    /**
     * Note moyenne attribuée à cet épisode.
     * Peut être utilisée pour les recommandations ou les tendances.
     */
    @Column(name = "note")
    private Double note;

    /**
     * Série à laquelle cet épisode appartient.
     * Relation {@code ManyToOne} avec {@link Series}.
     */
    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Series serie;

    /**
     * Définit la série à laquelle appartient cet épisode.
     *
     * @param series la série associée
     */
    public void setSeries(Series series) {
        this.serie = series;
    }
}
