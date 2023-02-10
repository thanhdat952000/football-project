package com.swp490.dasdi.infrastructure.reposiotory;

import com.swp490.dasdi.infrastructure.entity.Invitation;
import com.swp490.dasdi.infrastructure.entity.Pitch;
import com.swp490.dasdi.infrastructure.entity.enumeration.InvitationStatus;
import com.swp490.dasdi.infrastructure.entity.enumeration.InvitationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    @Query("select distinct i from Invitation i " +
            "where (i.district.city.id = ?1 or ?1 = '' or ?1 is null) " +
            "and (i.district.id = ?2 or ?2 = '' or ?2 is null) " +
            "and (i.team.name like concat('%', concat(?3, '%')) or ?3 = '' or ?3 is null ) " +
            "and (i.team.level.id in ?4) " +
            "and (i.invitationType in ?5) " +
            "and (i.status in ?6)")
    List<Invitation> filterByCondition(Long cityId, Long districtId, String teamName, List<Long> levelIds, List<InvitationType> invitationTypes, List<InvitationStatus> status, Pageable pageable);

    @Query("select distinct i from Invitation i " +
            "where i.team.teamBoss.id = ?1 " +
            "and i.status = ?2")
    Page<Invitation> findByTeamBossId(Long teamBossId, InvitationStatus invitationStatus, Pageable pageable);

    @Query("select distinct i from Invitation i " +
            "where i.competitor.teamBoss.id = ?1 " +
            "and i.status = ?2")
    Page<Invitation> findByCompetitorId(Long competitorId, InvitationStatus invitationStatus, Pageable pageable);
}
