package com.wam.lab1_maintenance.request;

import com.wam.lab1_maintenance.model.Series;

public record EpisodeRequestBody(
    Long id,
    String title,
    Integer episodeNumber,
    Double note,
    Series series
) {}
