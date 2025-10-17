package com.wam.lab1_maintenance.request;

import com.wam.lab1_maintenance.model.Gender;

/**
 * Représente le corps (body) d’une requête HTTP utilisée pour créer
 * ou mettre à jour un utilisateur (personne) dans l’application.
 *
 * Cette classe est un DTO (Data Transfer Object) qui permet de transférer
 * les données du client vers le serveur sans exposer directement l’entité {@code User}.
 *
 * Comme il s’agit d’un record, les valeurs sont immuables et seuls les getters sont générés automatiquement.
 */
public record PersonRequestBody(

        /**
         * Prénom de la personne.
         */
        String fname,

        /**
         * Nom de famille de la personne.
         */
        String lname,

        /**
         * Âge de la personne.
         */
        Integer age,

        /**
         * Genre de la personne (valeurs possibles définies dans {@link Gender}).
         */
        Gender gender
) {}
