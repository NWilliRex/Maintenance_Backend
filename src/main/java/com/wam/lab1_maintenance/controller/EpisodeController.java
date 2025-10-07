package com.wam.lab1_maintenance.controller;

import com.wam.lab1_maintenance.model.Episode;
import com.wam.lab1_maintenance.service.EpisodeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class EpisodeController {

    private final EpisodeService episodeService;

    @GetMapping("/episodes")
    public List<Episode> getAllEpisodes() throws FileNotFoundException {
        return episodeService.getAllEpisodes();
    }
}
