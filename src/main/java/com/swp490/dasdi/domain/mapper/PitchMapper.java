package com.swp490.dasdi.domain.mapper;

import com.swp490.dasdi.application.dto.response.PitchResponse;
import com.swp490.dasdi.infrastructure.entity.Pitch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, MiniPitchMapper.class, ReviewMapper.class})
public abstract class PitchMapper {

    @Mapping(target = "owner", expression = "java(pitch.getOwner().getFullName())")
    @Mapping(target = "imageUrls", expression = "java(pitch.getImages().stream().map(image -> image.getUrl()).collect(java.util.stream.Collectors.toList()))")
    @Mapping(target = "reviews", source = "reviewPitches")
    public abstract PitchResponse toPitchResponse(Pitch pitch);
}
