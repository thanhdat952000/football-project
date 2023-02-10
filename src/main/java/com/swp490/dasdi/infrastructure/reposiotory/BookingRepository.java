package com.swp490.dasdi.infrastructure.reposiotory;

import com.swp490.dasdi.infrastructure.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findByUserIdAndStatus(long userId, int status, Pageable pageable);

    @Query("select distinct b from Booking b " +
            "where b.miniPitch.pitch.id = ?1 " +
            "and b.status = ?2")
    Page<Booking> findByPitchIdAndStatus(long pitchId, int status, Pageable pageable);

    @Query("select distinct b from Booking b " +
            "where b.miniPitch.id = ?1 " +
            "and b.status in ?2")
    List<Booking> findByMiniPitchId(long miniPitchId, List<Integer> status);
}
