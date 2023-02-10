package com.swp490.dasdi.application.controller;

import com.swp490.dasdi.application.dto.request.team.TeamCreateDto;
import com.swp490.dasdi.application.dto.request.team.TeamRegisterApprovalDto;
import com.swp490.dasdi.application.dto.request.team.TeamRegisterDto;
import com.swp490.dasdi.application.dto.request.team.TeamUpdateDto;
import com.swp490.dasdi.application.dto.response.*;
import com.swp490.dasdi.domain.service.LevelService;
import com.swp490.dasdi.domain.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final LevelService levelService;

    @GetMapping("/list")
    public ResponseEntity<List<TeamResponse>> getAll(@RequestParam(required = false, defaultValue = "0") int page) {
        return new ResponseEntity<>(teamService.getAll(page), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<TeamResponse>> filterByCondition(@RequestParam(required = false) Long cityId,
                                                                @RequestParam(required = false) Long districtId,
                                                                @RequestParam(required = false) List<Long> levelIds,
                                                                @RequestParam(required = false) String keyword,
                                                                @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                                @RequestParam(required = false, defaultValue = "0") Integer page) {
        return new ResponseEntity<>(teamService.filterByCondition(cityId, districtId, levelIds, keyword, sortBy, page), HttpStatus.OK);
    }

    @GetMapping("/team-boss/{teamBossId}")
    public ResponseEntity<List<TeamResponse>> getByTeamBoss(@PathVariable long teamBossId) {
        return new ResponseEntity<>(teamService.getByTeamBoss(teamBossId), HttpStatus.OK);
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<TeamResponse>> getByPlayer(@PathVariable long playerId) {
        return new ResponseEntity<>(teamService.getByPlayer(playerId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse> getById(@PathVariable long id) {
        return new ResponseEntity<>(teamService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/change-status/{id}")
    public ResponseEntity<HttpResponse> changeStatus(@PathVariable long id, @RequestParam boolean status) {
        teamService.changeStatus(id, status);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "Change team's status successfully!");
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @GetMapping("/waiting-for-approval/{teamId}")
    public ResponseEntity<List<TeamRegisterResponse>> getListTeamRegister(@PathVariable long teamId) {
        return new ResponseEntity<>(teamService.getListTeamRegister(teamId), HttpStatus.OK);
    }

    @GetMapping("/levels")
    public ResponseEntity<List<LevelResponse>> getLevels() {
        return new ResponseEntity<>(levelService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> create(@ModelAttribute TeamCreateDto teamCreateDto) {
        UserResponse teamBoss = teamService.create(teamCreateDto);
        return new ResponseEntity<>(teamBoss, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpResponse> update(@PathVariable long id, @ModelAttribute TeamUpdateDto teamUpdateDto) {
        teamService.update(id, teamUpdateDto);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "Update team successfully!");
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(@RequestBody TeamRegisterDto teamRegisterDto) {
        teamService.register(teamRegisterDto);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "Register to team successfully!");
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @PostMapping("/approval")
    public ResponseEntity<HttpResponse> registerApproval(@RequestBody TeamRegisterApprovalDto teamRegisterApprovalDto) {
        teamService.registerApproval(teamRegisterApprovalDto);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "Approval register to team successfully!");
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @DeleteMapping("/reject")
    public ResponseEntity<HttpResponse> registerReject(@RequestParam Long teamRegisterId) {
        teamService.registerReject(teamRegisterId);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "Reject register successfully!");
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
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
