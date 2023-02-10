package com.swp490.dasdi.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mini_pitch")
public class MiniPitch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "pitch_id", nullable = false)
    private Pitch pitch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pitch_type_id", nullable = false)
    private PitchType pitchType;

    @OneToMany(mappedBy = "miniPitch", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Cost> costs;
}
