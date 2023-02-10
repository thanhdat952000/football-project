package com.swp490.dasdi.domain.service;

import com.swp490.dasdi.application.dto.response.RoleResponse;
import com.swp490.dasdi.infrastructure.entity.Role;

import java.util.List;

public interface RoleService {

    List<RoleResponse> getAll();

    Role getRoleUser();
}
