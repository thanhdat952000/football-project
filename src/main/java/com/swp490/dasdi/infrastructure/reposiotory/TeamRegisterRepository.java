package com.swp490.dasdi.infrastructure.reposiotory;

import com.swp490.dasdi.infrastructure.entity.TeamRegister;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRegisterRepository extends JpaRepository<TeamRegister, Long> {

    List<TeamRegister> getByTeamIdAndApprovalFlag(long teamId, boolean approvalFlag);
}
