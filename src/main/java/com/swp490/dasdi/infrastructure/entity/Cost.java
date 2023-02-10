package com.swp490.dasdi.infrastructure.entity;

import com.swp490.dasdi.infrastructure.entity.enumeration.DayWorkingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cost")
public class Cost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "hour_start")
    private LocalTime hourStart;

    @Column(name = "hour_end")
    private LocalTime hourEnd;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "day_working_type")
    private DayWorkingType dayWorkingType;

    @Column(name = "price")
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "mini_pitch_id", nullable = false)
    private MiniPitch miniPitch;
}
