package com.swp490.dasdi.domain.service.impl;

import com.swp490.dasdi.application.dto.response.CityResponse;
import com.swp490.dasdi.domain.mapper.CityMapper;
import com.swp490.dasdi.domain.service.CityService;
import com.swp490.dasdi.infrastructure.entity.City;
import com.swp490.dasdi.infrastructure.reposiotory.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Override
    public List<CityResponse> getAll() {
        return cityRepository.findAll().stream()
                .map(city -> cityMapper.toCityResponse(city))
                .collect(Collectors.toList());
    }

    @Override
    public CityResponse getById(long id) {
        City city = cityRepository.getReferenceById(id);
        return cityMapper.toCityResponse(city);
    }
}
