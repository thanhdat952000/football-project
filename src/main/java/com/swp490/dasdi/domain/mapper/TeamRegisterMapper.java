package com.swp490.dasdi.domain.mapper;

import com.swp490.dasdi.application.dto.response.TeamRegisterResponse;
import com.swp490.dasdi.infrastructure.entity.TeamRegister;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TeamRegisterMapper {

    @Mapping(target = "playerId", expression = "java(teamRegister.getPlayer().getId())")
    @Mapping(target = "playerName", expression = "java(teamRegister.getPlayer().getFullName())")
    @Mapping(target = "playerAvatarUrl", expression = "java(teamRegister.getPlayer().getAvatarUrl())")
    @Mapping(target = "playerFortePosition", expression = "java(teamRegister.getPlayer().getFortePosition())")
    @Mapping(target = "playerHeight", expression = "java(teamRegister.getPlayer().getHeight())")
    @Mapping(target = "playerWeight", expression = "java(teamRegister.getPlayer().getWeight())")
    TeamRegisterResponse toTeamRegisterResponse(TeamRegister teamRegister);
}
