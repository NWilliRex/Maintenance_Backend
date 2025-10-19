package com.wam.lab1_maintenance.repository;


import com.wam.lab1_maintenance.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findByUser_Id(Long userId);
}
