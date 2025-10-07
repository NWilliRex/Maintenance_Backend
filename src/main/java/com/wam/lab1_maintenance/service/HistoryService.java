package com.wam.lab1_maintenance.service;

import com.wam.lab1_maintenance.model.Episode;
import com.wam.lab1_maintenance.model.History;
import com.wam.lab1_maintenance.model.User;
import com.wam.lab1_maintenance.repository.EpisodeRepository;
import com.wam.lab1_maintenance.repository.HistoryRepository;
import com.wam.lab1_maintenance.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HistoryService {
    private final UserRepository userRepository;
    private final EpisodeRepository episodeRepository;
    private final HistoryRepository historyRepository;

    public List<Episode> searchHistoryPerson(Long userId) {
        return historyRepository.findByUser_Id(userId).stream()
                            .map(History::getEpisode)
                            .toList();
    }

    public void addEpisode(Long userId, Long episodeId){
        User user = userRepository.findById(userId)
                            .orElseThrow(() -> new EntityNotFoundException("Erreur! Le user n'a pas été trouvé"));

        Episode episode = episodeRepository.findById(episodeId)
                            .orElseThrow(() -> new EntityNotFoundException("Erreur! L'épisode n'a pas été trouvé"));

        History history = History.builder()
                                .episode(episode)
                                .user(user)
                            .build();

        historyRepository.save(history);

    }
}
