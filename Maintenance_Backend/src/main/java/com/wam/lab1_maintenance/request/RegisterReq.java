package com.wam.lab1_maintenance.request;

/**
 * Représente le corps (body) d’une requête HTTP pour l’enregistrement d’un nouvel utilisateur.
 *
 * Ce record agit comme un DTO (Data Transfer Object) permettant de transférer
 * les informations saisies par un utilisateur lors de son inscription,
 * sans exposer directement l’entité {@code User}.
 *
 * Comme il s’agit d’un record, les champs sont immuables
 * et seuls des accesseurs (getters implicites) sont générés automatiquement.
 */
public record RegisterReq(

        /**
         * Le prénom de l’utilisateur.
         */
        String fname,

        /**
         * Le nom de famille de l’utilisateur.
         */
        String lname,

        /**
         * Le nom d’utilisateur unique utilisé pour la connexion.
         */
        String username,

        /**
         * L’âge de l’utilisateur.
         */
        Integer age,

        /**
         * Le mot de passe de l’utilisateur.
         */
        String password
) {}
