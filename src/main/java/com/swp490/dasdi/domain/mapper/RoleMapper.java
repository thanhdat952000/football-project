package com.swp490.dasdi.domain.mapper;

import com.swp490.dasdi.application.dto.response.RoleResponse;
import com.swp490.dasdi.infrastructure.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "name", expression = "java(role.getName().toUpperCase())")
    @Mapping(target = "permissions", expression = "java(role.getPermissions().stream().map(permission -> permission.getDescription()).collect(java.util.stream.Collectors.toList()))")
    RoleResponse toRoleResponse(Role role);
}
