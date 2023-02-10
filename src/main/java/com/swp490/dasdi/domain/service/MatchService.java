package com.swp490.dasdi.domain.service;

import com.swp490.dasdi.application.dto.request.match.MatchCreateDto;
import com.swp490.dasdi.application.dto.response.MatchResponse;
import org.springframework.data.domain.Page;

public interface MatchService {

    void create(MatchCreateDto matchCreateDto);

    void confirm(long matchId, int confirm);

    Page<MatchResponse> getByHomeTeamBossId(long homePitchBossId, Integer page);

    Page<MatchResponse> getByAwayTeamBossId(long awayTeamBossId, Integer page);
}
