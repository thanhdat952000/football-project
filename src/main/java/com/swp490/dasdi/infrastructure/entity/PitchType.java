package com.swp490.dasdi.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pitch_type")
public class PitchType {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
}
