package com.wam.lab1_maintenance;

import com.wam.lab1_maintenance.model.Episode;
import com.wam.lab1_maintenance.repository.EpisodeRepository;
import com.wam.lab1_maintenance.service.EpisodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class EpisodeServiceTest {

    @Autowired
    private EpisodeService episodeService;

    @Autowired
    private EpisodeRepository episodeRepository;

    private Episode episode1;
    private Episode episode2;

    @BeforeEach
    void setUp() {
        // Création et sauvegarde de deux épisodes dans la base de données
        episode1 = episodeRepository.save(Episode.builder()
                .title("Episode 1")
                .episodeNumber(1)
                .note(4.5)
                .build());

        episode2 = episodeRepository.save(Episode.builder()
                .title("Episode 2")
                .episodeNumber(2)
                .note(3.8)
                .build());
    }

    @Test
    void getAllEpisodes_ShouldReturnAllEpisodes() {
        // when
        List<Episode> episodes = episodeService.getAllEpisodes();

        // then
        assertThat(episodes)
                .hasSize(2)
                .extracting(Episode::getTitle)
                .containsExactlyInAnyOrder("Episode 1", "Episode 2");
    }

    @Test
    void getAllEpisodes_ShouldReturnEmptyList_WhenNoEpisodesExist() {
        // given
        episodeRepository.deleteAll();

        // when
        List<Episode> episodes = episodeService.getAllEpisodes();

        // then
        assertThat(episodes).isEmpty();
    }
}
