package com.wam.lab1_maintenance.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "episode")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Episode {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "episode_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "episode_number", nullable = false)
    private Integer episodeNumber;

    @Column(name = "note")
    private Double note;

    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Series serie;

  
    public void setSeries(Series series) {
        this.serie = series;
    }
}
