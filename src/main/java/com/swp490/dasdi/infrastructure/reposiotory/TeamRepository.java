package com.swp490.dasdi.infrastructure.reposiotory;

import com.swp490.dasdi.infrastructure.entity.Team;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("select distinct t from Team t " +
            "where (t.address.district.city.id = ?1 or ?1 = '' or ?1 is null) " +
            "and (t.address.district.id = ?2 or ?2 = '' or ?2 is null) " +
            "and (t.level.id in ?3) " +
            "and ((t.name like concat('%', concat(?4, '%')) or t.address.detail like concat('%', concat(?4, '%'))) or ?4 = '' or ?4 is null )")
    List<Team> filterByCondition(Long cityId, Long districtId, List<Long> levelIds, String keyword, Pageable pageable);

    List<Team> findByTeamBossId(long teamBossId);
}
