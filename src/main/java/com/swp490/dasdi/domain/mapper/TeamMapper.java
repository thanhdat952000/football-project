package com.swp490.dasdi.domain.mapper;

import com.swp490.dasdi.application.dto.response.TeamResponse;
import com.swp490.dasdi.infrastructure.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, UserMapper.class, TeamRegisterMapper.class, MatchMapper.class})
public interface TeamMapper {

    @Mapping(target = "levelId", expression = "java(team.getLevel().getId())")
    @Mapping(target = "level", expression = "java(team.getLevel().getName())")
    @Mapping(target = "homePitchId", expression = "java(java.util.Objects.nonNull(team.getHomePitch()) ? team.getHomePitch().getId() : null)")
    @Mapping(target = "homePitchName", expression = "java(java.util.Objects.nonNull(team.getHomePitch()) ? team.getHomePitch().getName() : null)")
    @Mapping(target = "teamBossId", expression = "java(team.getTeamBoss().getId())")
    @Mapping(target = "teamBoss", expression = "java(team.getTeamBoss().getFullName())")
    @Mapping(target = "imageUrls", expression = "java(team.getImages().stream().map(image -> image.getUrl()).collect(java.util.stream.Collectors.toList()))")
    TeamResponse toTeamResponse(Team team);
}
