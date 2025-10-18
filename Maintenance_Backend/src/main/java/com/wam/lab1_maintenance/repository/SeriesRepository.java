package com.wam.lab1_maintenance.repository;

import com.wam.lab1_maintenance.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface SeriesRepository extends JpaRepository<Series, Long> {

    List<Series> findByGenre(String genre);

    List<Series> findByTitleStartingWith(String title);

    List<Series> findByNbEpisodesGreaterThanEqual(int nbEpisodes);
}
