package com.wam.lab1_maintenance.controller;

import com.wam.lab1_maintenance.model.Series;
import com.wam.lab1_maintenance.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

     @GetMapping("/users/{id}/recommendations")
    public Map<String, List<Series>> getRecommendations(@PathVariable("id") Long id) {
         return recommendationService.recommendationForUser(id);
     }
}
