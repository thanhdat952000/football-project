package com.swp490.dasdi.domain.mapper;

import com.swp490.dasdi.application.dto.response.PitchTypeResponse;
import com.swp490.dasdi.infrastructure.entity.PitchType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PitchTypeMapper {

    PitchTypeResponse toPitchTypeResponse(PitchType pitchType);
}
