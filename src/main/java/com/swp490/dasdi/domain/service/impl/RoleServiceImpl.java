package com.swp490.dasdi.domain.service.impl;

import com.swp490.dasdi.application.dto.response.RoleResponse;
import com.swp490.dasdi.domain.mapper.RoleMapper;
import com.swp490.dasdi.domain.service.RoleService;
import com.swp490.dasdi.infrastructure.entity.Role;
import com.swp490.dasdi.infrastructure.reposiotory.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    private static final long ROLE_USER = 1L;

    @Override
    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream()
                .map(role -> roleMapper.toRoleResponse(role))
                .collect(Collectors.toList());
    }

    @Override
    public Role getRoleUser() {
        return roleRepository.getById(ROLE_USER);
    }
}
