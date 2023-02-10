package com.swp490.dasdi.domain.mapper;

import com.swp490.dasdi.application.dto.response.CostResponse;
import com.swp490.dasdi.infrastructure.entity.Cost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CostMapper {

    @Mapping(target = "dayWorkingType", expression = "java(com.swp490.dasdi.infrastructure.entity.enumeration.DayWorkingType.of(cost.getDayWorkingType()))")
    CostResponse toCostResponse(Cost cost);
}
