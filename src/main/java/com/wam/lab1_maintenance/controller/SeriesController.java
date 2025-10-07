package com.wam.lab1_maintenance.controller;

import com.wam.lab1_maintenance.model.Series;
import com.wam.lab1_maintenance.request.SeriesRequestBody;
import com.wam.lab1_maintenance.service.SerieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class SeriesController {
    
    private final SerieService seriesService;

    @GetMapping("/search")
    public List<Series> searchSeries(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer nbEpisodes) {
        return seriesService.searchSeriesPersonalised(genre, title, nbEpisodes);
    }

    @GetMapping("/series")
    public List<Series> getAllSeries() {
        return seriesService.getAllSeries();
    }

    @GetMapping("/series/{id}")
    public Series getSeriesDetails(@PathVariable Long id) {
        return seriesService.getSerieById(id);
    }

    @PostMapping("/addSeries")
    public Series addSeries(@RequestBody SeriesRequestBody body) {
        return seriesService.addSeries(body);
    }

    @PutMapping("/updateSeries/{id}")
    public Series updateSeries(@PathVariable Long id, @RequestBody SeriesRequestBody body) {
        return seriesService.updateSeries(id, body);
    }

    @DeleteMapping("/deleteSeries/{id}")
    public void deleteSeries(@PathVariable Long id) {
        seriesService.deleteSeries(id);
    }
}
