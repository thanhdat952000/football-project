package com.swp490.dasdi.domain.mapper;

import com.swp490.dasdi.application.dto.response.MiniPitchResponse;
import com.swp490.dasdi.infrastructure.entity.MiniPitch;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CostMapper.class, PitchTypeMapper.class})
public interface MiniPitchMapper {

    MiniPitchResponse toMiniPitchResponse(MiniPitch miniPitch);
}
