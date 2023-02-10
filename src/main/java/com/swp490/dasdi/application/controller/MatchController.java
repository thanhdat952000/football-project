package com.swp490.dasdi.application.controller;

import com.swp490.dasdi.application.dto.request.match.MatchCreateDto;
import com.swp490.dasdi.application.dto.response.HttpResponse;
import com.swp490.dasdi.application.dto.response.MatchResponse;
import com.swp490.dasdi.domain.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping("/create")
    public ResponseEntity<HttpResponse> create(@RequestBody MatchCreateDto matchCreateDto) {
        matchService.create(matchCreateDto);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "Create a match successfully!");
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @GetMapping("/confirm/{matchId}")
    public ResponseEntity<HttpResponse> confirm(@PathVariable long matchId, @RequestParam int confirm) {
        matchService.confirm(matchId, confirm);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "Confirm a match successfully!");
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @GetMapping("/home-team-boss/{homeTeamBossId}")
    public ResponseEntity<Page<MatchResponse>> getByHomeTeamId(@PathVariable long homeTeamBossId,
                                                               @RequestParam(required = false, defaultValue = "0") Integer page) {
        return new ResponseEntity<>(matchService.getByHomeTeamBossId(homeTeamBossId, page), HttpStatus.OK);
    }

    @GetMapping("/away-team-boss/{awayTeamBossId}")
    public ResponseEntity<Page<MatchResponse>> getByAwayTeamId(@PathVariable long awayTeamBossId,
                                                               @RequestParam(required = false, defaultValue = "0") Integer page) {
        return new ResponseEntity<>(matchService.getByAwayTeamBossId(awayTeamBossId, page), HttpStatus.OK);
    }

    private HttpResponse setHttpResponse(HttpStatus httpStatus, String message) {
        return HttpResponse.builder()
                .timeStamp(LocalDateTime.now())
                .httpStatusCode(httpStatus.value())
                .httpStatus(httpStatus)
                .reason(httpStatus.getReasonPhrase().toUpperCase())
                .message(message)
                .build();
    }
}
