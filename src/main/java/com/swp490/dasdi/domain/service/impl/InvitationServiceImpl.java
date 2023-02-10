package com.swp490.dasdi.domain.service.impl;

import com.swp490.dasdi.application.dto.request.invitation.InvitationCreateDto;
import com.swp490.dasdi.application.dto.request.invitation.InvitationRegisterDto;
import com.swp490.dasdi.application.dto.request.invitation.InvitationUpdateDto;
import com.swp490.dasdi.application.dto.response.InvitationResponse;
import com.swp490.dasdi.application.dto.response.InvitationTypeResponse;
import com.swp490.dasdi.domain.exception.InvitationCannotRegisterException;
import com.swp490.dasdi.domain.mapper.InvitationMapper;
import com.swp490.dasdi.domain.service.InvitationService;
import com.swp490.dasdi.infrastructure.entity.*;
import com.swp490.dasdi.infrastructure.entity.enumeration.InvitationStatus;
import com.swp490.dasdi.infrastructure.entity.enumeration.InvitationType;
import com.swp490.dasdi.infrastructure.reposiotory.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class
InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final PitchRepository pitchRepository;
    private final DistrictRepository districtRepository;
    private final CityRepository cityRepository;
    private final TeamRepository teamRepository;
    private final InvitationMapper invitationMapper;
    private final List<Long> LEVEL_IDs_DEFAULT = List.of(1L, 2L, 3L, 4L, 5L);
    private final List<InvitationType> INVITATION_TYPES = List.of(InvitationType.FUNNY, InvitationType.FRIENDSHIP, InvitationType.ALL_IN);
    private final int PAGE_SIZE = 6;

    @Override
    public List<InvitationResponse> getAll(int page) {
        return invitationRepository.findAll(PageRequest.of(page, PAGE_SIZE)).stream()
                .map(invitation -> invitationMapper.toInvitationResponse(invitation))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvitationResponse> filterByCondition(Long cityId, Long districtId, String teamName, List<Long> levelIds, List<Integer> invitationTypes, String sortBy, Integer page) {
        List<InvitationType> invitationTypeList = new ArrayList<>();
        if (Objects.nonNull(invitationTypes)) {
            invitationTypeList = invitationTypes.stream()
                    .map(invitationType -> InvitationType.values()[invitationType])
                    .collect(Collectors.toList());
        }
        List<InvitationStatus> invalidStatus = List.of(InvitationStatus.ACTIVATING, InvitationStatus.WAITING_FOR_APPROVE);
        List<Invitation> invitations = invitationRepository.filterByCondition(cityId, districtId, teamName, Objects.nonNull(levelIds) ? levelIds : LEVEL_IDs_DEFAULT, Objects.nonNull(invitationTypes) ? invitationTypeList : INVITATION_TYPES, invalidStatus, PageRequest.of(page, PAGE_SIZE, Sort.by(sortBy)));
        this.checkInvitationOutOfDate(invitations);
        return invitations.stream()
                .map(invitation -> invitationMapper.toInvitationResponse(invitation))
                .collect(Collectors.toList());
    }

    @Override
    public Page<InvitationResponse> getByTeamBossId(long teamBossId, Integer status, Integer page) {
        Page<Invitation> invitations = invitationRepository.findByTeamBossId(teamBossId, InvitationStatus.values()[status], PageRequest.of(page, PAGE_SIZE));
        this.checkInvitationOutOfDate(invitations.getContent());
        return new PageImpl<>(invitations.stream()
                .map(invitation -> invitationMapper.toInvitationResponse(invitation))
                .collect(Collectors.toList()), PageRequest.of(page, PAGE_SIZE), invitations.getTotalElements());
    }

    @Override
    public Page<InvitationResponse> getByCompetitorId(long competitorId, Integer status, Integer page) {
        Page<Invitation> invitations = invitationRepository.findByCompetitorId(competitorId, InvitationStatus.values()[status], PageRequest.of(page, PAGE_SIZE));
        this.checkInvitationOutOfDate(invitations.getContent());
        return new PageImpl<>(invitations.stream()
                .map(invitation -> invitationMapper.toInvitationResponse(invitation))
                .collect(Collectors.toList()), PageRequest.of(page, PAGE_SIZE), invitations.getTotalElements());
    }

    @Override
    public InvitationResponse getById(long id) {
        Invitation invitation = this.findInvitationById(id);
        return invitationMapper.toInvitationResponse(invitation);
    }

    @Override
    @Transactional
    public void create(InvitationCreateDto invitationCreateDto) {
        Team team = this.findTeamById(invitationCreateDto.getTeamId());

        City city = this.findCityById(invitationCreateDto.getCityId());

        District district = null;
        if (Objects.nonNull(invitationCreateDto.getDistrictId())) {
            district = this.findDistrictById(invitationCreateDto.getDistrictId());
        }

        Pitch pitch = null;
        if (Objects.nonNull(invitationCreateDto.getPitchId())) {
            pitch = this.findPitchById(invitationCreateDto.getPitchId());
        }

        Invitation invitation = Invitation.builder()
                .message(invitationCreateDto.getMessage())
                .time(LocalDateTime.parse(invitationCreateDto.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .invitationType(InvitationType.values()[invitationCreateDto.getInvitationType()])
                .status(InvitationStatus.ACTIVATING)
                .createdDate(LocalDateTime.now())
                .team(team)
                .city(city)
                .district(district)
                .pitch(pitch)
                .build();

        invitationRepository.save(invitation);
    }

    @Override
    @Transactional
    public void update(long id, InvitationUpdateDto invitationUpdateDto) {
        Invitation invitation = this.findInvitationById(invitationUpdateDto.getId());

        if (Objects.nonNull(invitationUpdateDto.getMessage())) {
            invitation.setMessage(invitationUpdateDto.getMessage());
        }
        if (Objects.nonNull(invitationUpdateDto.getInvitationType())) {
            invitation.setInvitationType(InvitationType.values()[invitationUpdateDto.getInvitationType()]);
        }
        if (Objects.nonNull(invitationUpdateDto.getTime())) {
            invitation.setTime(LocalDateTime.parse(invitationUpdateDto.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
        if (Objects.nonNull(invitationUpdateDto.getTeamId())) {
            invitation.setTeam(this.findTeamById(invitationUpdateDto.getTeamId()));
        }
        if (Objects.nonNull(invitationUpdateDto.getCityId())) {
            invitation.setCity(this.findCityById(invitationUpdateDto.getCityId()));
        }
        if (Objects.nonNull(invitationUpdateDto.getDistrictId())) {
            invitation.setDistrict(this.findDistrictById(invitationUpdateDto.getDistrictId()));
        }
        if (Objects.nonNull(invitationUpdateDto.getPitchId())) {
            invitation.setPitch(this.findPitchById(invitationUpdateDto.getPitchId()));
        }
        invitation.setStatus(InvitationStatus.ACTIVATING);
        invitation.setUpdatedDate(LocalDateTime.now());

        invitationRepository.save(invitation);
    }

    @Override
    @Transactional
    public void delete(Long invitationId) {
        Invitation invitation = this.findInvitationById(invitationId);
        invitationRepository.delete(invitation);
    }

    @Override
    public List<InvitationTypeResponse> getAllInvitationTypes() {
        return InvitationType.invitationTypeResponseMap.entrySet().stream()
                .map(entry -> InvitationTypeResponse.builder()
                        .id(entry.getKey())
                        .name(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void register(InvitationRegisterDto invitationRegisterDto) {
        Team competitor = this.findTeamById(invitationRegisterDto.getCompetitorId());
        Invitation invitation = this.findInvitationById(invitationRegisterDto.getInvitationId());
        if (Objects.nonNull(invitation.getCompetitor())) {
            throw new InvitationCannotRegisterException("Cannot register in this invitation!");
        }
        invitation.setCompetitor(competitor);
        invitation.setStatus(InvitationStatus.WAITING_FOR_APPROVE);

        invitationRepository.save(invitation);
    }

    @Override
    @Transactional
    public void registerApproval(Long invitationId) {
        Invitation invitation = this.findInvitationById(invitationId);
        invitation.setStatus(InvitationStatus.IN_PROGRESS);
        invitationRepository.save(invitation);
    }

    @Override
    @Transactional
    public void registerReject(Long invitationId) {
        Invitation invitation = this.findInvitationById(invitationId);
        invitation.setStatus(InvitationStatus.ACTIVATING);
        invitation.setCompetitor(null);
        invitationRepository.save(invitation);
    }

    private Invitation findInvitationById(Long id) {
        return invitationRepository.getReferenceById(id);
    }

    private Pitch findPitchById(long id) {
        return pitchRepository.getReferenceById(id);
    }

    private Team findTeamById(long id) {
        return teamRepository.getReferenceById(id);
    }

    private District findDistrictById(long id) {
        return districtRepository.getReferenceById(id);
    }

    private City findCityById(long id) {
        return cityRepository.getReferenceById(id);
    }

    private void checkInvitationOutOfDate(List<Invitation> invitations) {
        invitations.stream()
                .filter(invitation -> invitation.getStatus().equals(InvitationStatus.ACTIVATING) || invitation.getStatus().equals(InvitationStatus.WAITING_FOR_APPROVE))
                .filter(invitation -> LocalDateTime.now().isAfter(invitation.getTime()))
                .forEach(invitation -> {
                    invitation.setStatus(InvitationStatus.OUT_OF_DATE);
                    invitationRepository.save(invitation);
                });
    }
}