package com.swp490.dasdi.domain.mapper;

import com.swp490.dasdi.application.dto.response.MatchResponse;
import com.swp490.dasdi.infrastructure.entity.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TeamMapper.class, DistrictMapper.class, PitchMapper.class})
public interface MatchMapper {

    @Mapping(target = "homeTeamId", expression = "java(match.getHomeTeam().getId())")
    @Mapping(target = "homeTeamName", expression = "java(match.getHomeTeam().getName())")
    @Mapping(target = "homeTeamLogoUrl", expression = "java(match.getHomeTeam().getLogoUrl())")
    @Mapping(target = "homeTeamLevel", expression = "java(match.getHomeTeam().getLevel().getName())")
    @Mapping(target = "awayTeamId", expression = "java(match.getAwayTeam().getId())")
    @Mapping(target = "awayTeamName", expression = "java(match.getAwayTeam().getName())")
    @Mapping(target = "awayTeamLogoUrl", expression = "java(match.getAwayTeam().getLogoUrl())")
    @Mapping(target = "awayTeamLevel", expression = "java(match.getAwayTeam().getLevel().getName())")
    @Mapping(target = "pitchId", expression = "java(java.util.Objects.nonNull(match.getPitch()) ? match.getPitch().getId() : null)")
    @Mapping(target = "pitchName", expression = "java(java.util.Objects.nonNull(match.getPitch()) ? match.getPitch().getName() : null)")
    MatchResponse toMatchResponse(Match match);
}
