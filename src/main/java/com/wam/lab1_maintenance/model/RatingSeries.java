package com.wam.lab1_maintenance.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ratings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingSeries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ratings_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Series series;

    @Column(name="user_rating")
    private Float userRating;
}
