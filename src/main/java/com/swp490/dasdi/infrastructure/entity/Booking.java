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
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "time_start")
    private LocalDateTime timeStart;

    @Column(name = "time_end")
    private LocalDateTime timeEnd;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @Column(name = "payment")
    private Integer payment;

    @Column(name = "price")
    private Integer price;

    @Column(name = "status")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mini_pitch_id", nullable = false)
    private MiniPitch miniPitch;
}
