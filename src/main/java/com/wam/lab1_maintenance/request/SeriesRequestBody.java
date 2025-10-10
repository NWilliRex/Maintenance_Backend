package com.wam.lab1_maintenance.request;

public record SeriesRequestBody(
    String title,
    String genre,
    Integer nbEpisodes,
    Float note
) {}

