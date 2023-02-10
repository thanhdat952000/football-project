package com.swp490.dasdi.application.controller;

import com.swp490.dasdi.application.dto.request.pitch.BookingCreateDto;
import com.swp490.dasdi.application.dto.request.pitch.PitchCreateDto;
import com.swp490.dasdi.application.dto.request.pitch.PitchUpdateDto;
import com.swp490.dasdi.application.dto.request.pitch.ReviewPitchCreateDto;
import com.swp490.dasdi.application.dto.response.HttpResponse;
import com.swp490.dasdi.application.dto.response.PitchResponse;
import com.swp490.dasdi.application.dto.response.PitchTypeResponse;
import com.swp490.dasdi.application.dto.response.UserResponse;
import com.swp490.dasdi.domain.service.PitchService;
import com.swp490.dasdi.domain.service.PitchTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/pitch")
@RequiredArgsConstructor
public class PitchController {

    private final PitchService pitchService;
    private final PitchTypeService pitchTypeService;

    @GetMapping("/list")
    public ResponseEntity<List<PitchResponse>> getAll(@RequestParam(required = false, defaultValue = "0") int page) {
        return new ResponseEntity<>(pitchService.getAll(page), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<PitchResponse>> filterByCondition(@RequestParam(required = false) Long cityId,
                                                                 @RequestParam(required = false) Long districtId,
                                                                 @RequestParam(required = false) Integer minPrice,
                                                                 @RequestParam(required = false) Integer maxPrice,
                                                                 @RequestParam(required = false) List<Long> pitchTypeIds,
                                                                 @RequestParam(required = false) String keyword,
                                                                 @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                                 @RequestParam(required = false, defaultValue = "0") Integer page) {
        return new ResponseEntity<>(pitchService.filterByCondition(cityId, districtId, pitchTypeIds, minPrice, maxPrice, keyword, sortBy, page), HttpStatus.OK);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PitchResponse>> getByOwnerId(@PathVariable long ownerId) {
        return new ResponseEntity<>(pitchService.getByOwnerId(ownerId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PitchResponse> getById(@PathVariable long id) {
        return new ResponseEntity<>(pitchService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/change-status/{id}")
    public ResponseEntity<HttpResponse> changeStatus(@PathVariable long id, @RequestParam int status) {
        pitchService.changeStatus(id, status);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "Update pitch' status successfully!");
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @GetMapping("/type")
    public ResponseEntity<List<PitchTypeResponse>> getAllPitchType() {
        return new ResponseEntity<>(pitchTypeService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> create(@ModelAttribute PitchCreateDto pitchCreateDto) {
        UserResponse owner = pitchService.create(pitchCreateDto);
        return new ResponseEntity<>(owner, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpResponse> update(@PathVariable long id, @ModelAttribute PitchUpdateDto pitchUpdateDto) {
        pitchService.update(id, pitchUpdateDto);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "Update pitch successfully!");
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @PostMapping("/review")
    public ResponseEntity<HttpResponse> reviewPitch(@RequestBody ReviewPitchCreateDto reviewPitchCreateDto) {
        pitchService.reviewPitch(reviewPitchCreateDto);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "Review pitch successfully!");
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @GetMapping("/approve-creation")
    public ResponseEntity<Page<PitchResponse>> getByStatus(@RequestParam int status,
                                                           @RequestParam(required = false, defaultValue = "0") Integer page) {
        return new ResponseEntity<>(pitchService.getByStatus(status, page), HttpStatus.OK);
    }

    @GetMapping("/approval")
    public ResponseEntity<HttpResponse> registerApproval(@RequestParam Long pitchId) {
        pitchService.registerApproval(pitchId);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "Approval create a pitch successfully!");
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @GetMapping("/change-status-pitch/{pitchId}")
    public ResponseEntity<HttpResponse> changeStatusPitch(@PathVariable Long pitchId, @RequestParam Integer status) {
        pitchService.changeStatusPitch(pitchId, status);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "Change status of pitch successfully!");
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @DeleteMapping("/reject")
    public ResponseEntity<HttpResponse> registerReject(@RequestParam Long pitchId) {
        pitchService.registerReject(pitchId);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "Reject create successfully!");
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
