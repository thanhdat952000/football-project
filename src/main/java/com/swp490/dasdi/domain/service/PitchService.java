package com.swp490.dasdi.domain.service;

import com.swp490.dasdi.application.dto.request.pitch.PitchCreateDto;
import com.swp490.dasdi.application.dto.request.pitch.PitchUpdateDto;
import com.swp490.dasdi.application.dto.request.pitch.ReviewPitchCreateDto;
import com.swp490.dasdi.application.dto.response.PitchResponse;
import com.swp490.dasdi.application.dto.response.UserResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PitchService {

    List<PitchResponse> getAll(int page);

    List<PitchResponse> filterByCondition(Long cityId, Long districtId, List<Long> pitchTypeIds, Integer minPrice, Integer maxPrice, String keyword, String sortBy, Integer page);

    PitchResponse getById(long id);

    List<PitchResponse> getByOwnerId(long ownerId);

    UserResponse create(PitchCreateDto pitchCreateDto);

    void update(long id, PitchUpdateDto pitchUpdateDto);

    Page<PitchResponse> getByStatus(int status, Integer page);

    void registerReject(long pitchId);

    void registerApproval(long pitchId);

    void changeStatus(long id, int status);

    void changeStatusPitch(Long pitchId, Integer status);

    void reviewPitch(ReviewPitchCreateDto reviewPitchCreateDto);
}
