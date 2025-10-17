package com.wam.lab1_maintenance;

import com.wam.lab1_maintenance.model.Episode;
import com.wam.lab1_maintenance.model.History;
import com.wam.lab1_maintenance.model.User;
import com.wam.lab1_maintenance.repository.EpisodeRepository;
import com.wam.lab1_maintenance.repository.HistoryRepository;
import com.wam.lab1_maintenance.repository.UserRepository;
import com.wam.lab1_maintenance.service.HistoryService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HistoryServiceTest {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private HistoryRepository historyRepository;

    private User user;
    private Episode episode1;
    private Episode episode2;

    @BeforeEach
    void setUp() {
        // Création d'un utilisateur
        user = userRepository.save(User.builder()
                .fname("John")
                .lname("Doe")
                .build());

        // Création d'épisodes
        episode1 = episodeRepository.save(Episode.builder()
                .title("Episode 1")
                .episodeNumber(1)
                .note(4.2)
                .build());

        episode2 = episodeRepository.save(Episode.builder()
                .title("Episode 2")
                .episodeNumber(2)
                .note(3.9)
                .build());

        // Historique initial
        historyRepository.save(History.builder().user(user).episode(episode1).build());
        historyRepository.save(History.builder().user(user).episode(episode2).build());
    }

    @Test
    void searchHistoryPerson_ShouldReturnAllEpisodesForUser() {
        List<Episode> episodes = historyService.searchHistoryPerson(user.getId());

        assertThat(episodes)
                .hasSize(2)
                .extracting(Episode::getTitle)
                .containsExactlyInAnyOrder("Episode 1", "Episode 2");
    }

    @Test
    void searchHistoryPerson_ShouldReturnEmptyList_WhenNoHistoryExists() {
        User newUser = userRepository.save(User.builder()
                .fname("Jane")
                .lname("Smith")
                .build());

        List<Episode> episodes = historyService.searchHistoryPerson(newUser.getId());

        assertThat(episodes).isEmpty();
    }

    @Test
    void addEpisode_ShouldAddEpisodeToHistory_WhenUserAndEpisodeExist() {
        Episode newEpisode = episodeRepository.save(Episode.builder()
                .title("Episode 3")
                .episodeNumber(3)
                .note(4.8)
                .build());

        historyService.addEpisode(user.getId(), newEpisode.getId());

        List<History> histories = historyRepository.findByUser_Id(user.getId());
        assertThat(histories)
                .hasSize(3)
                .extracting(h -> h.getEpisode().getTitle())
                .contains("Episode 3");
    }

    @Test
    void addEpisode_ShouldThrowException_WhenUserOrEpisodeNotFound() {
        Long invalidUserId = 999L;
        Long invalidEpisodeId = 888L;

        // User not found
        assertThatThrownBy(() -> historyService.addEpisode(invalidUserId, episode1.getId()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("user n'a pas été trouvé");

        // Episode not found
        assertThatThrownBy(() -> historyService.addEpisode(user.getId(), invalidEpisodeId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("épisode n'a pas été trouvé");
    }
}
