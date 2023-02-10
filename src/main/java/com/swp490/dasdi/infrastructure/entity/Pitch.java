package com.swp490.dasdi.infrastructure.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pitch")
public class Pitch {
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

    @Column(name = "hour_start")
    private LocalTime hourStart;

    @Column(name = "hour_end")
    private LocalTime hourEnd;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "pitch", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<MiniPitch> miniPitches;

    @OneToMany(mappedBy = "pitch", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Image> images;

    @OneToMany(mappedBy = "pitch")
    @Fetch(FetchMode.SUBSELECT)
    private List<ReviewPitch> reviewPitches;
}
