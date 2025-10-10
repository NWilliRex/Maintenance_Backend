package com.wam.lab1_maintenance.controller;

import com.wam.lab1_maintenance.model.Series;
import com.wam.lab1_maintenance.service.TrendingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TrendingController {

    private final TrendingService trendingService;

    @GetMapping("/series/trending")
    public List<Series> getTrendingSeries() {
        return trendingService.topTendance();
    }
}
