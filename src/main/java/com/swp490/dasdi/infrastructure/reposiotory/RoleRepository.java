package com.swp490.dasdi.infrastructure.reposiotory;

import com.swp490.dasdi.infrastructure.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select distinct r from Role r " +
            "join fetch r.permissions " +
            "where r.id = ?1")
    Role getById(long id);
}
