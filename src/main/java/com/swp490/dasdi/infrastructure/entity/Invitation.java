package com.swp490.dasdi.infrastructure.entity;

import com.swp490.dasdi.infrastructure.entity.enumeration.InvitationStatus;
import com.swp490.dasdi.infrastructure.entity.enumeration.InvitationType;
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
@Table(name = "invitation")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "time")
    private LocalDateTime time;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "invitation_type")
    private InvitationType invitationType;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private InvitationStatus status;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "competitor_id")
    private Team competitor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pitch_id")
    private Pitch pitch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pitch_type_id")
    private PitchType pitchType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;
}
