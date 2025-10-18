package com.wam.lab1_maintenance.request;

import lombok.Builder;

/**
 * Représente la réponse envoyée après une authentification réussie.
 * Cette classe contient le jeton (token) JWT généré par le serveur,
 * qui sera ensuite utilisé par le client pour s'authentifier
 * lors des requêtes suivantes.
 *
 * Utilise un "record" Java pour garantir l’immuabilité et
 * simplifier la gestion des objets de transfert de données (DTO).
 */
@Builder
public record AuthRes(

        /**
         * Jeton JWT (JSON Web Token) attribué à l'utilisateur après connexion.
         * Ce jeton est utilisé pour authentifier et autoriser les requêtes ultérieures.
         */
        String token
) {}
