package com.swp490.dasdi.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id", nullable = false)
    private Team homeTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team_id", nullable = false)
    private Team awayTeam;

    @Column(name = "home_team_score")
    private Integer homeTeamScore;

    @Column(name = "away_team_score")
    private Integer awayTeamScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pitch_id", nullable = false)
    private Pitch pitch;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
