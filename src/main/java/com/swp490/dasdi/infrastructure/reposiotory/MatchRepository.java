package com.swp490.dasdi.infrastructure.reposiotory;

import com.swp490.dasdi.infrastructure.entity.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("select distinct m from Match m " +
            "where m.homeTeam.teamBoss.id = ?1")
    Page<Match> findByHomeTeamBossId(Long homeTeamId, Pageable pageable);

    @Query("select distinct m from Match m " +
            "where m.awayTeam.teamBoss.id = ?1")
    Page<Match> findByAwayTeamBossId(Long awayTeamId, Pageable pageable);
}
