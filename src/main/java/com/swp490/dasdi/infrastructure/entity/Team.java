package com.swp490.dasdi.infrastructure.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "recruit_flag")
    private Boolean recruitFlag;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_pitch_id")
    private Pitch homePitch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_boss_id", nullable = false)
    private User teamBoss;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<TeamRegister> teamRegisters;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "team_player", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "player_id"))
    private List<User> players;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Image> images;

    @OneToMany(mappedBy = "homeTeam", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Match> matches;
}
