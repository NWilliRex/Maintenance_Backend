package com.wam.lab1_maintenance.request;

import com.wam.lab1_maintenance.model.Series;

/**
 * Représente le corps (body) d’une requête HTTP utilisée pour créer
 * ou mettre à jour un épisode dans la base de données.
 *
 * Cette classe est un DTO (Data Transfer Object) permettant
 * de transférer les données entre le client et le serveur
 * sans exposer directement l’entité JPA.
 *
 * Utilise un "record" pour bénéficier de l’immuabilité et d’un code concis.
 */
public record EpisodeRequestBody(

        /**
         * Identifiant unique de l’épisode (optionnel lors de la création).
         */
        Long id,

        /**
         * Titre de l’épisode.
         */
        String title,

        /**
         * Numéro de l’épisode dans la série.
         * Par exemple, épisode 1, 2, etc.
         */
        Integer episodeNumber,

        /**
         * Note attribuée à l’épisode (peut être moyenne ou personnelle).
         */
        Double note,

        /**
         * Série à laquelle appartient l’épisode.
         * Il s’agit d’une relation vers l’entité {@link Series}.
         */
        Series series
) {}
