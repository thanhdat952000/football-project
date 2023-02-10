package com.swp490.dasdi.domain.mapper;

import com.swp490.dasdi.application.dto.response.BookingResponse;
import com.swp490.dasdi.infrastructure.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "pitchId", expression = "java(booking.getMiniPitch().getPitch().getId())")
    @Mapping(target = "pitchName", expression = "java(booking.getMiniPitch().getPitch().getName())")
    @Mapping(target = "pitchLogoUrl", expression = "java(booking.getMiniPitch().getPitch().getLogoUrl())")
    @Mapping(target = "pitchCity", expression = "java(booking.getMiniPitch().getPitch().getAddress().getDistrict().getCity().getName())")
    @Mapping(target = "pitchType", expression = "java(booking.getMiniPitch().getPitchType().getName())")
    @Mapping(target = "userName", expression = "java(booking.getUser().getFullName())")
    @Mapping(target = "userAvatarUrl", expression = "java(booking.getUser().getAvatarUrl())")
    BookingResponse toBookingResponse(Booking booking);
}
