package com.wam.lab1_maintenance.request;

import com.wam.lab1_maintenance.model.User;
import lombok.Builder;

/**
 * Représente le corps (body) d’une requête HTTP utilisée pour ajouter
 * ou mettre à jour la note (rating) qu’un utilisateur attribue à une série.
 *
 * Ce record sert de DTO (Data Transfer Object) pour transférer
 * les données du client vers le serveur sans exposer directement l’entité {@code RatingSeries}.
 *
 * Comme il s’agit d’un record, les valeurs sont immuables et seuls les getters sont générés automatiquement.
 */
@Builder
public record RatingRequestBody(

        /**
         * L’utilisateur qui attribue la note à la série.
         */
        User user,

        /**
         * La note donnée par l’utilisateur (entre 0 et 5, par exemple).
         */
        Float userRating
) {}
