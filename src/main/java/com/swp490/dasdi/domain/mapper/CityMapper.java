package com.swp490.dasdi.domain.mapper;

import com.swp490.dasdi.application.dto.response.CityResponse;
import com.swp490.dasdi.infrastructure.entity.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = DistrictMapper.class)
public interface CityMapper {

    CityResponse toCityResponse(City city);
}
