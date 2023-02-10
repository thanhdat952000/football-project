package com.swp490.dasdi.domain.service.impl;

import com.swp490.dasdi.application.dto.response.LevelResponse;
import com.swp490.dasdi.domain.mapper.LevelMapper;
import com.swp490.dasdi.domain.service.LevelService;
import com.swp490.dasdi.infrastructure.reposiotory.LevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LevelServiceImpl implements LevelService {

    private final LevelRepository levelRepository;
    private final LevelMapper levelMapper;

    @Override
    public List<LevelResponse> getAll() {
        return levelRepository.findAll().stream()
                .map(level -> levelMapper.toLevelResponse(level))
                .collect(Collectors.toList());
    }
}
