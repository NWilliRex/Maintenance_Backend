package com.wam.lab1_maintenance.request;

/**
 * Représente le corps (body) d’une requête HTTP pour créer ou mettre à jour une série.
 *
 * Ce record agit comme un DTO (Data Transfer Object) servant à transférer
 * les informations d’une série sans exposer directement l’entité {@code Series}.
 *
 * Les champs sont immuables et les accesseurs (getters implicites)
 * sont automatiquement générés par le record.
 */
public record SeriesRequestBody(

        /**
         * Le titre de la série.
         */
        String title,

        /**
         * Le genre principal de la série (ex. : Drame, Comédie, Action, etc.).
         */
        String genre,

        /**
         * Le nombre total d’épisodes que contient la série.
         */
        Integer nbEpisodes,

        /**
         * La note moyenne attribuée à la série.
         */
        Float note
) {}
