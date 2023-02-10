package com.swp490.dasdi.domain.mapper;

import com.swp490.dasdi.application.dto.response.UserResponse;
import com.swp490.dasdi.infrastructure.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

    @Mapping(target = "preferredFoot", expression = "java(com.swp490.dasdi.infrastructure.entity.enumeration.PreferredFoot.of(user.getPreferredFoot()))")
    UserResponse toUserResponse(User user);
}
