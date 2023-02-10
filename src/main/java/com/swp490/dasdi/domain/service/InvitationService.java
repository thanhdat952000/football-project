package com.swp490.dasdi.domain.service;

import com.swp490.dasdi.application.dto.request.invitation.InvitationCreateDto;
import com.swp490.dasdi.application.dto.request.invitation.InvitationRegisterDto;
import com.swp490.dasdi.application.dto.request.invitation.InvitationUpdateDto;
import com.swp490.dasdi.application.dto.response.InvitationResponse;
import com.swp490.dasdi.application.dto.response.InvitationTypeResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InvitationService {

    List<InvitationResponse> getAll(int page);

    List<InvitationResponse> filterByCondition(Long cityId, Long districtId, String teamName, List<Long> levelIds, List<Integer> invitationTypes, String sortBy, Integer page);

    Page<InvitationResponse> getByTeamBossId(long teamBossId, Integer status, Integer page);

    Page<InvitationResponse> getByCompetitorId(long competitorId, Integer status, Integer page);

    InvitationResponse getById(long id);

    void create(InvitationCreateDto invitationCreateDto);

    void update(long id, InvitationUpdateDto invitationUpdateDto);

    List<InvitationTypeResponse> getAllInvitationTypes();

    void register(InvitationRegisterDto invitationRegisterDto);

    void registerApproval(Long invitationId);

    void registerReject(Long invitationId);

    void delete(Long invitationId);
}
