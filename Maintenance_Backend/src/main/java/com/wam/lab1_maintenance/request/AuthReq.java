package com.wam.lab1_maintenance.request;

import lombok.Builder;

/**
 * Représente une requête d'authentification envoyée par l'utilisateur.
 * Cette classe est utilisée lors de la connexion pour transporter
 * le nom d'utilisateur et le mot de passe.
 *
 * Utilise un "record" Java, ce qui rend la classe immuable
 * et simplifie la création d'objets de type DTO (Data Transfer Object).
 */
@Builder
public record AuthReq(

        /**
         * Nom d'utilisateur saisi par l'utilisateur lors de la connexion.
         */
        String username,

        /**
         * Mot de passe associé au nom d'utilisateur.
         */
        String password
) {}
