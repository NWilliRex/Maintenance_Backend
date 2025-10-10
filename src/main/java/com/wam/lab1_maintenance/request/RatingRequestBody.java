package com.wam.lab1_maintenance.request;

import com.wam.lab1_maintenance.model.User;
import lombok.Builder;

@Builder
public record RatingRequestBody(
        User user,
        Float userRating
) {}
