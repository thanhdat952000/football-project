package com.swp490.dasdi.domain.service;

import com.swp490.dasdi.application.dto.request.team.TeamCreateDto;
import com.swp490.dasdi.application.dto.request.team.TeamRegisterApprovalDto;
import com.swp490.dasdi.application.dto.request.team.TeamRegisterDto;
import com.swp490.dasdi.application.dto.request.team.TeamUpdateDto;
import com.swp490.dasdi.application.dto.response.TeamRegisterResponse;
import com.swp490.dasdi.application.dto.response.TeamResponse;
import com.swp490.dasdi.application.dto.response.UserResponse;

import java.util.List;

public interface TeamService {

    List<TeamResponse> getAll(int page);

    List<TeamResponse> filterByCondition(Long cityId, Long districtId, List<Long> levelIds, String keyword, String sortBy, Integer page);

    List<TeamResponse> getByTeamBoss(long teamBossId);

    List<TeamResponse> getByPlayer(long playerId);

    TeamResponse getById(long id);

    UserResponse create(TeamCreateDto teamCreateDto);

    void update(long id, TeamUpdateDto teamUpdateDto);

    void register(TeamRegisterDto teamRegisterDto);

    void registerApproval(TeamRegisterApprovalDto teamRegisterApprovalDto);

    void registerReject(Long teamRegisterId);

    void changeStatus(long id, boolean status);

    List<TeamRegisterResponse> getListTeamRegister(Long teamId);
}
