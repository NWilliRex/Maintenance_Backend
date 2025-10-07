package com.wam.lab1_maintenance.repository;

import com.wam.lab1_maintenance.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface SeriesRepository extends JpaRepository<Series, Long> {

}
