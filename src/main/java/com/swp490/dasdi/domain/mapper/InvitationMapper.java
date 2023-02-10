package com.swp490.dasdi.domain.mapper;

import com.swp490.dasdi.application.dto.response.InvitationResponse;
import com.swp490.dasdi.infrastructure.entity.Invitation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TeamMapper.class, DistrictMapper.class, PitchMapper.class})
public interface InvitationMapper {

    @Mapping(target = "teamId", expression = "java(invitation.getTeam().getId())")
    @Mapping(target = "teamName", expression = "java(invitation.getTeam().getName())")
    @Mapping(target = "teamBossId", expression = "java(invitation.getTeam().getTeamBoss().getId())")
    @Mapping(target = "teamLogoUrl", expression = "java(invitation.getTeam().getLogoUrl())")
    @Mapping(target = "teamLevel", expression = "java(invitation.getTeam().getLevel().getName())")
    @Mapping(target = "competitorId", expression = "java(java.util.Objects.nonNull(invitation.getCompetitor()) ? invitation.getCompetitor().getId() : null)")
    @Mapping(target = "competitorName", expression = "java(java.util.Objects.nonNull(invitation.getCompetitor()) ? invitation.getCompetitor().getName() : null)")
    @Mapping(target = "competitorLogoUrl", expression = "java(java.util.Objects.nonNull(invitation.getCompetitor()) ? invitation.getCompetitor().getLogoUrl() : null)")
    @Mapping(target = "competitorLevel", expression = "java(java.util.Objects.nonNull(invitation.getCompetitor()) ? invitation.getCompetitor().getLevel().getName() : null)")
    @Mapping(target = "city", expression = "java(invitation.getCity().getName())")
    @Mapping(target = "cityId", expression = "java(invitation.getCity().getId())")
    @Mapping(target = "district", expression = "java(java.util.Objects.nonNull(invitation.getDistrict()) ? invitation.getDistrict().getName() : null)")
    @Mapping(target = "districtId", expression = "java(java.util.Objects.nonNull(invitation.getDistrict()) ? invitation.getDistrict().getId() : null)")
    @Mapping(target = "pitchId", expression = "java(java.util.Objects.nonNull(invitation.getPitch()) ? invitation.getPitch().getId() : null)")
    @Mapping(target = "pitchName", expression = "java(java.util.Objects.nonNull(invitation.getPitch()) ? invitation.getPitch().getName() : null)")
    @Mapping(target = "pitchType", expression = "java(java.util.Objects.nonNull(invitation.getPitchType()) ? invitation.getPitchType().getName() : null)")
    @Mapping(target = "invitationType", expression = "java(com.swp490.dasdi.infrastructure.entity.enumeration.InvitationType.of(invitation.getInvitationType()))")
    @Mapping(target = "status", expression = "java(com.swp490.dasdi.infrastructure.entity.enumeration.InvitationStatus.of(invitation.getStatus()))")
//    @Mapping(target = "competitor", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    InvitationResponse toInvitationResponse(Invitation invitation);
}
