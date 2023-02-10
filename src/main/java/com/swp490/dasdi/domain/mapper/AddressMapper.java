package com.swp490.dasdi.domain.mapper;

import com.swp490.dasdi.application.dto.response.AddressResponse;
import com.swp490.dasdi.infrastructure.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "district", expression = "java(address.getDistrict().getName())")
    @Mapping(target = "city", expression = "java(address.getDistrict().getCity().getName())")
    @Mapping(target = "districtId", expression = "java(address.getDistrict().getId())")
    @Mapping(target = "cityId", expression = "java(address.getDistrict().getCity().getId())")
    AddressResponse toAddressResponse(Address address);
}
