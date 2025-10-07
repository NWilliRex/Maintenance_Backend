package com.wam.lab1_maintenance.repository;

import com.wam.lab1_maintenance.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface EpisodeRepository extends JpaRepository<Episode, Long> {

}
