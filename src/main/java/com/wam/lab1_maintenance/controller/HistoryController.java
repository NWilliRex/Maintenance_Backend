package com.wam.lab1_maintenance.controller;

import com.wam.lab1_maintenance.model.Episode;
import com.wam.lab1_maintenance.service.HistoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/user/{userId}/history")
    public List<Episode> historyForUser(@PathVariable("userId") Long userId) {
        return historyService.searchHistoryPerson(userId);
    }

    @PostMapping("/users/{id}/history/{episodeId}")
    public void addEpisodeToUser(@PathVariable("id") Long userId, @PathVariable("episodeId") Long episodeId) {
        historyService.addEpisode(userId, episodeId);
    }
}
