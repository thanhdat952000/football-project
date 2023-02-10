package com.swp490.dasdi.domain.service;

import com.swp490.dasdi.application.dto.response.CityResponse;

import java.util.List;

public interface CityService {

    List<CityResponse> getAll();

    CityResponse getById(long id);
}
