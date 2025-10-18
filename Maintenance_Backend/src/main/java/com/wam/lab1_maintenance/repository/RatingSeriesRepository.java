package com.wam.lab1_maintenance.repository;

import com.wam.lab1_maintenance.model.RatingSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface RatingSeriesRepository extends JpaRepository<RatingSeries, Long> {
    RatingSeries findByUserIdAndSeriesId(Long userId, Long seriesId);
}
