package com.wam.lab1_maintenance.service;

import com.wam.lab1_maintenance.model.Episode;
import com.wam.lab1_maintenance.repository.EpisodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EpisodeService {

    private final EpisodeRepository episodeRepository;

    public List<Episode> getAllEpisodes() {
        return episodeRepository.findAll();
    }
}
