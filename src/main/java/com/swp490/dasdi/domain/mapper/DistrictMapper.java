package com.swp490.dasdi.domain.mapper;

import com.swp490.dasdi.application.dto.response.DistrictResponse;
import com.swp490.dasdi.infrastructure.entity.District;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DistrictMapper {

    DistrictResponse toDistrictResponse(District district);
}
