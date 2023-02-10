package com.swp490.dasdi.infrastructure.reposiotory;

import com.swp490.dasdi.infrastructure.entity.Pitch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PitchRepository extends JpaRepository<Pitch, Long> {

    @Query("select distinct p from Pitch p " +
            "inner join p.miniPitches mp " +
            "inner join mp.pitchType pt " +
            "inner join mp.costs cs " +
            "where (p.address.district.city.id = ?1 or ?1 = '' or ?1 is null) " +
            "and (p.address.district.id = ?2 or ?2 = '' or ?2 is null) " +
            "and (pt.id in ?3) " +
            "and ((cs.price between ?4 and ?5) or (?4 = ''  or ?5 = '') or (?4 is null  or ?5 is null)) " +
            "and ((p.name like concat('%', concat(?6, '%')) or p.address.detail like concat('%', concat(?6, '%'))) or ?6 = '' or ?6 is null) " +
            "and p.status = ?7")
    List<Pitch> filterByCondition(Long cityId, Long districtId, List<Long> pitchTypeIds, Integer minPrice, Integer maxPrice, String keyword, Integer status, Pageable pageable);

    List<Pitch> findByOwnerId(Long ownerId);

    Page<Pitch> findByStatus(int status, Pageable pageable);
}
