package com.swp490.dasdi.domain.mapper;

import com.swp490.dasdi.application.dto.response.AddressResponse;
import com.swp490.dasdi.application.dto.response.ReviewResponse;
import com.swp490.dasdi.infrastructure.entity.Address;
import com.swp490.dasdi.infrastructure.entity.ReviewPitch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "username", expression = "java(reviewPitch.getUser().getFullName())")
    @Mapping(target = "userAvatarUrl", expression = "java(reviewPitch.getUser().getAvatarUrl())")
    ReviewResponse toReviewResponse(ReviewPitch reviewPitch);
}
