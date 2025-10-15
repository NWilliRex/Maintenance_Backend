package com.wam.lab1_maintenance.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "episode_id")
    private Episode episode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "watched_at")
    private LocalDateTime watchedAt;

}
