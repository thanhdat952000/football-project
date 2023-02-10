package com.swp490.dasdi.application.controller;

import com.swp490.dasdi.application.dto.request.invitation.InvitationCreateDto;
import com.swp490.dasdi.application.dto.request.invitation.InvitationRegisterDto;
import com.swp490.dasdi.application.dto.request.invitation.InvitationUpdateDto;
import com.swp490.dasdi.application.dto.response.HttpResponse;
import com.swp490.dasdi.application.dto.response.InvitationResponse;
import com.swp490.dasdi.application.dto.response.InvitationTypeResponse;
import com.swp490.dasdi.domain.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/invitation")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    @GetMapping("/list")
    public ResponseEntity<List<InvitationResponse>> getAll(@RequestParam(required = false, defaultValue = "0") int page) {
        return new ResponseEntity<>(invitationService.getAll(page), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvitationResponse> getById(@PathVariable long id) {
        return new ResponseEntity<>(invitationService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<InvitationResponse>> filterByCondition(@RequestParam(required = false) Long cityId,
                                                                      @RequestParam(required = false) Long districtId,
                                                                      @RequestParam(required = false) String teamName,
                                                                      @RequestParam(required = false) List<Long> levelIds,
                                                                      @RequestParam(required = false) List<Integer> invitationTypes,
                                                                      @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                                      @RequestParam(required = false, defaultValue = "0") Integer page) {
        return new ResponseEntity<>(invitationService.filterByCondition(cityId, districtId, teamName, levelIds, invitationTypes, sortBy, page), HttpStatus.OK);
    }

    @GetMapping("/team-boss/{teamBossId}")
    public ResponseEntity<Page<InvitationResponse>> getByTeamBossId(@PathVariable long teamBossId,
                                                                    @RequestParam Integer status,
                                                                    @RequestParam(required = false, defaultValue = "0") int page) {
        return new ResponseEntity<>(invitationService.getByTeamBossId(teamBossId, status, page), HttpStatus.OK);
    }

    @GetMapping("/competitor/{competitorId}")
    public ResponseEntity<Page<InvitationResponse>> getByCompetitorId(@PathVariable long competitorId,
                                                                      @RequestParam Integer status,
                                                                      @RequestParam(required = false, defaultValue = "0") Integer page) {
        return new ResponseEntity<>(invitationService.getByCompetitorId(competitorId, status, page), HttpStatus.OK);
    }

    @GetMapping("/types")
    public ResponseEntity<List<InvitationTypeResponse>> getAllInvitationTypes() {
        return new ResponseEntity<>(invitationService.getAllInvitationTypes(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpResponse> create(@RequestBody InvitationCreateDto invitationCreateDto) {
        invitationService.create(invitationCreateDto);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "Create invitation successfully!");
        return new ResponseEntity<>(httpResponse, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpResponse> update(@PathVariable long id, @RequestBody InvitationUpdateDto invitationUpdateDto) {
        invitationService.update(id, invitationUpdateDto);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "Update invitation successfully!");
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpResponse> delete(@RequestParam Long invitationId) {
        invitationService.delete(invitationId);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "Delete invitation successfully!");
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(@RequestBody InvitationRegisterDto invitationRegisterDto) {
        invitationService.register(invitationRegisterDto);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "Register to invitation successfully!");
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @GetMapping("/approval")
    public ResponseEntity<HttpResponse> registerApproval(@RequestParam Long invitationId) {
        invitationService.registerApproval(invitationId);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "Register to invitation successfully!");
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @DeleteMapping("/reject")
    public ResponseEntity<HttpResponse> registerReject(@RequestParam Long invitationId) {
        invitationService.registerReject(invitationId);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "Reject invitation successfully!");
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
