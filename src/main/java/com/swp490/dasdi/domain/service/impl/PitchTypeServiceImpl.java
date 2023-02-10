package com.swp490.dasdi.domain.service.impl;

import com.swp490.dasdi.application.dto.response.PitchTypeResponse;
import com.swp490.dasdi.domain.mapper.PitchTypeMapper;
import com.swp490.dasdi.domain.service.PitchTypeService;
import com.swp490.dasdi.infrastructure.reposiotory.PitchTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PitchTypeServiceImpl implements PitchTypeService {

    private final PitchTypeRepository pitchTypeRepository;
    private final PitchTypeMapper pitchTypeMapper;

    @Override
    public List<PitchTypeResponse> getAll() {
        return pitchTypeRepository.findAll().stream()
                .map(pitchType -> pitchTypeMapper.toPitchTypeResponse(pitchType))
                .collect(Collectors.toList());
    }
}
