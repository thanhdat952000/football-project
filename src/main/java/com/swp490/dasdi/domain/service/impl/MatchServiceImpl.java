package com.swp490.dasdi.domain.service.impl;

import com.swp490.dasdi.application.dto.request.match.MatchCreateDto;
import com.swp490.dasdi.application.dto.response.MatchResponse;
import com.swp490.dasdi.domain.mapper.MatchMapper;
import com.swp490.dasdi.domain.service.MatchService;
import com.swp490.dasdi.infrastructure.entity.Invitation;
import com.swp490.dasdi.infrastructure.entity.Match;
import com.swp490.dasdi.infrastructure.entity.Pitch;
import com.swp490.dasdi.infrastructure.entity.Team;
import com.swp490.dasdi.infrastructure.entity.enumeration.InvitationStatus;
import com.swp490.dasdi.infrastructure.reposiotory.InvitationRepository;
import com.swp490.dasdi.infrastructure.reposiotory.MatchRepository;
import com.swp490.dasdi.infrastructure.reposiotory.PitchRepository;
import com.swp490.dasdi.infrastructure.reposiotory.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final InvitationRepository invitationRepository;
    private final TeamRepository teamRepository;
    private final PitchRepository pitchRepository;
    private final MatchMapper matchMapper;
    private final int WAITING_FOR_CONFIRM = 0;
    private final int CONFIRMED = 1;
    private final int REJECT_RESULT = 2;
    private final int PAGE_SIZE = 6;

    @Override
    @Transactional
    public void create(MatchCreateDto matchCreateDto) {
        Invitation invitation = invitationRepository.getReferenceById(matchCreateDto.getInvitationId());
        invitation.setStatus(InvitationStatus.DONE);
        invitationRepository.save(invitation);

        Team homeTeam = this.findTeamById(matchCreateDto.getHomeTeamId());
        Team awayTeam = this.findTeamById(matchCreateDto.getAwayTeamId());

        Pitch pitch = null;
        if (Objects.nonNull(matchCreateDto.getPitchId())) {
            pitch = this.findPitchById(matchCreateDto.getPitchId());
        }

        Match match = Match.builder()
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .homeTeamScore(matchCreateDto.getHomeTeamScore())
                .awayTeamScore(matchCreateDto.getAwayTeamScore())
                .pitch(pitch)
                .time(LocalDateTime.parse(matchCreateDto.getTime()))
                .status(WAITING_FOR_CONFIRM)
                .createdDate(LocalDateTime.now())
                .build();
        matchRepository.save(match);
    }

    @Override
    @Transactional
    public void confirm(long matchId, int confirm) {
        Match match = matchRepository.getReferenceById(matchId);
        match.setStatus(confirm);
        if (confirm == REJECT_RESULT) {
            match.setHomeTeamScore(null);
            match.setAwayTeamScore(null);
        }
        matchRepository.save(match);
    }

    @Override
    public Page<MatchResponse> getByHomeTeamBossId(long homePitchBossId, Integer page) {
        Page<Match> matches = matchRepository.findByHomeTeamBossId(homePitchBossId, PageRequest.of(page, PAGE_SIZE));
        return new PageImpl<>(matches.stream()
                .map(match -> matchMapper.toMatchResponse(match))
                .collect(Collectors.toList()), PageRequest.of(page, PAGE_SIZE), matches.getTotalElements());
    }

    @Override
    public Page<MatchResponse> getByAwayTeamBossId(long awayTeamBossId, Integer page) {
        Page<Match> matches = matchRepository.findByAwayTeamBossId(awayTeamBossId, PageRequest.of(page, PAGE_SIZE));
        return new PageImpl<>(matches.stream()
                .map(match -> matchMapper.toMatchResponse(match))
                .collect(Collectors.toList()), PageRequest.of(page, PAGE_SIZE), matches.getTotalElements());
    }

    private Team findTeamById(long id) {
        return teamRepository.getReferenceById(id);
    }

    private Pitch findPitchById(long id) {
        return pitchRepository.getReferenceById(id);
    }
}
