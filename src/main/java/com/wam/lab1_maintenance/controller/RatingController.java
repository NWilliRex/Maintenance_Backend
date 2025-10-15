package com.wam.lab1_maintenance.controller;

import com.wam.lab1_maintenance.model.RatingSeries;
import com.wam.lab1_maintenance.request.RatingRequestBody;
import com.wam.lab1_maintenance.service.RatingSerieService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RatingController {

    private final RatingSerieService ratingsService;

    @PostMapping(
            value = "/ratings/users/{userId}/series/{seriesId}",
            consumes = MediaType.TEXT_PLAIN_VALUE
    )
    public void addUserSeriesRating(@PathVariable Long userId, @PathVariable Long seriesId, @RequestBody String ratingBody) {
        Float rating = Float.valueOf(ratingBody.trim());
        ratingsService.addUserSeriesRating(userId, seriesId, rating);
    }

    @GetMapping("/ratings/users/{userId}/series/{seriesId}")
    public Float getUserSeriesRating(@PathVariable Long userId, @PathVariable Long seriesId) {
        return ratingsService.getUserSeriesRating(userId, seriesId);
    }

    @GetMapping("/ratings/series/{id}")
    public Float getSeriesRating(@PathVariable Long id) {
        return ratingsService.getSeriesRating(id);
    }

    @PutMapping("/ratings/users/{userId}/series/{seriesId}")
    public RatingSeries updateUserSeriesRating(@PathVariable Long userId, @PathVariable Long seriesId, @RequestBody RatingRequestBody body) {
        return ratingsService.updateUserSeriesRating(userId, seriesId, body);
    }
}