package com.swp490.dasdi.domain.service.impl;

import com.swp490.dasdi.application.dto.request.team.TeamCreateDto;
import com.swp490.dasdi.application.dto.request.team.TeamRegisterApprovalDto;
import com.swp490.dasdi.application.dto.request.team.TeamRegisterDto;
import com.swp490.dasdi.application.dto.request.team.TeamUpdateDto;
import com.swp490.dasdi.application.dto.response.TeamRegisterResponse;
import com.swp490.dasdi.application.dto.response.TeamResponse;
import com.swp490.dasdi.application.dto.response.UserResponse;
import com.swp490.dasdi.domain.exception.DistrictNotFoundException;
import com.swp490.dasdi.domain.mapper.TeamMapper;
import com.swp490.dasdi.domain.mapper.TeamRegisterMapper;
import com.swp490.dasdi.domain.mapper.UserMapper;
import com.swp490.dasdi.domain.service.TeamService;
import com.swp490.dasdi.domain.service.UploadFileService;
import com.swp490.dasdi.infrastructure.entity.*;
import com.swp490.dasdi.infrastructure.reposiotory.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamRegisterRepository teamRegisterRepository;
    private final PitchRepository pitchRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DistrictRepository districtRepository;
    private final LevelRepository levelRepository;
    private final UploadFileService uploadFileService;
    private final TeamMapper teamMapper;
    private final UserMapper userMapper;
    private final TeamRegisterMapper teamRegisterMapper;
    private final List<Long> LEVEL_IDs_DEFAULT = List.of(1L, 2L, 3L, 4L, 5L);
    private final boolean DELETED = false;
    private final boolean ACTIVE = true;
    private final int PAGE_SIZE = 6;

    @Override
    public List<TeamResponse> getAll(int page) {
        return teamRepository.findAll(PageRequest.of(page, PAGE_SIZE)).stream().map(team -> teamMapper.toTeamResponse(team)).collect(Collectors.toList());
    }

    @Override
    public List<TeamResponse> filterByCondition(Long cityId, Long districtId, List<Long> levelIds, String keyword, String sortBy, Integer page) {
        return teamRepository.filterByCondition(cityId, districtId, Objects.nonNull(levelIds) ? levelIds : LEVEL_IDs_DEFAULT, keyword, PageRequest.of(page, PAGE_SIZE, Sort.by(sortBy))).stream()
                .map(team -> teamMapper.toTeamResponse(team))
                .collect(Collectors.toList());
    }

    @Override
    public List<TeamResponse> getByTeamBoss(long teamBossId) {
        return teamRepository.findByTeamBossId(teamBossId)
                .stream().map(team -> teamMapper.toTeamResponse(team))
                .collect(Collectors.toList());
    }

    @Override
    public List<TeamResponse> getByPlayer(long playerId) {
        User user = userRepository.getReferenceById(playerId);
        return user.getTeam().stream()
                .map(team -> teamMapper.toTeamResponse(team))
                .collect(Collectors.toList());
    }

    @Override
    public TeamResponse getById(long id) {
        Team team = this.findTeamById(id);
        List<Match> matches = team.getMatches().stream()
                .filter(match -> match.getStatus() == 1)
                .collect(Collectors.toList());
        team.setMatches(matches);
        return teamMapper.toTeamResponse(team);
    }

    @Override
    @Transactional
    public void changeStatus(long id, boolean status) {
        Team team = this.findTeamById(id);
        team.setStatus(status);
        teamRepository.save(team);
    }

    @Override
    @Transactional
    public UserResponse create(TeamCreateDto teamCreateDto) {
        User teamBoss = this.findUserById(teamCreateDto.getTeamBossId());
        if (teamBoss.getRole() == roleRepository.getReferenceById(3L) || teamBoss.getRole() == roleRepository.getReferenceById(4L)) {
            teamBoss.setRole(roleRepository.getReferenceById(4L));
        } else {
            teamBoss.setRole(roleRepository.getReferenceById(2L));
        }

        District district = this.findDistrictById(teamCreateDto.getDistrictId());

        Level level = this.findLevelById(teamCreateDto.getLevelId());

        Address address = Address.builder()
                .detail(teamCreateDto.getAddressDetail())
                .phoneNumber(teamCreateDto.getPhoneNumber())
                .facebook(teamCreateDto.getFacebook())
                .mail(teamCreateDto.getMail())
                .latitude(teamCreateDto.getLatitude())
                .longitude(teamCreateDto.getLongitude())
                .district(district)
                .build();

        Pitch homePitch = null;
        if (Objects.nonNull(teamCreateDto.getHomePitchId())) {
            homePitch = this.findPitchById(teamCreateDto.getHomePitchId());
        }

        String logoUrl = this.uploadFile(teamCreateDto.getLogo(), teamCreateDto.getName());

        List<Image> images = teamCreateDto.getImages().stream()
                .map(file -> Image.builder()
                        .url(this.uploadFile(file, file.getName()))
                        .build())
                .collect(Collectors.toList());

        Team team = Team.builder()
                .name(teamCreateDto.getName())
                .address(address)
                .level(level)
                .logoUrl(logoUrl)
                .status(ACTIVE)
                .recruitFlag(teamCreateDto.getRecruitFlag())
                .createdDate(LocalDateTime.now())
                .description(teamCreateDto.getDescription())
                .homePitch(homePitch)
                .teamBoss(teamBoss)
                .images(new ArrayList<>())
                .build();

        team.getImages().addAll(images);
        images.forEach(image -> image.setTeam(team));
        teamRepository.save(team);
        return userMapper.toUserResponse(userRepository.save(teamBoss));
    }

    @Override
    @Transactional
    public void update(long id, TeamUpdateDto teamUpdateDto) {
        Team team = this.findTeamById(teamUpdateDto.getId());

        if (Objects.nonNull(teamUpdateDto.getName())) {
            team.setName(teamUpdateDto.getName());
        }
        if (Objects.nonNull(teamUpdateDto.getDescription())) {
            team.setDescription(teamUpdateDto.getDescription());
        }
        if (Objects.nonNull(teamUpdateDto.getLevelId())) {
            team.setLevel(this.findLevelById(teamUpdateDto.getLevelId()));
        }

        // update address
        Address address = team.getAddress();
        if (Objects.nonNull(teamUpdateDto.getAddressDetail())) {
            address.setDetail(teamUpdateDto.getAddressDetail());
        }
        if (Objects.nonNull(teamUpdateDto.getPhoneNumber())) {
            address.setPhoneNumber(teamUpdateDto.getPhoneNumber());
        }
        if (Objects.nonNull(teamUpdateDto.getFacebook())) {
            address.setFacebook(teamUpdateDto.getFacebook());
        }
        if (Objects.nonNull(teamUpdateDto.getMail())) {
            address.setMail(teamUpdateDto.getMail());
        }
        if (Objects.nonNull(teamUpdateDto.getDistrictId())) {
            address.setDistrict(this.findDistrictById(teamUpdateDto.getDistrictId()));
        }
        team.setAddress(address);

        // update home pitch
        if (Objects.nonNull(teamUpdateDto.getHomePitchId())) {
            team.setHomePitch(this.findPitchById(teamUpdateDto.getHomePitchId()));
        } else {
            team.setHomePitch(null);
        }

        // update logo
        if (Objects.nonNull(teamUpdateDto.getLogo())) {
            team.setLogoUrl(this.uploadFile(teamUpdateDto.getLogo(), teamUpdateDto.getName()));
        }

        // update image
        if (Objects.nonNull(teamUpdateDto.getImages())) {
            List<Image> images = teamUpdateDto.getImages().stream()
                    .map(file -> Image.builder()
                            .url(this.uploadFile(file, file.getName()))
                            .build())
                    .collect(Collectors.toList());
            team.setImages(images);
        }

        team.setUpdatedDate(LocalDateTime.now());
        teamRepository.save(team);
    }

    @Override
    @Transactional
    public void register(TeamRegisterDto teamRegisterDto) {
        Team team = this.findTeamById(teamRegisterDto.getTeamId());
        User player = this.findUserById(teamRegisterDto.getPlayerId());

        TeamRegister teamRegister = TeamRegister.builder()
                .team(team)
                .player(player)
                .createdDate(LocalDateTime.now())
                .approvalFlag(false)
                .build();

        teamRegisterRepository.save(teamRegister);
    }

    @Override
    @Transactional
    public void registerApproval(TeamRegisterApprovalDto teamRegisterApprovalDto) {
        TeamRegister teamRegister = this.findTeamRegisterById(teamRegisterApprovalDto.getTeamRegisterId());
        Team team = this.findTeamById(teamRegisterApprovalDto.getTeamId());
        User player = this.findUserById(teamRegisterApprovalDto.getPlayerId());

        team.getPlayers().add(player);
        teamRegister.setApprovalFlag(true);

        teamRepository.save(team);
        teamRegisterRepository.save(teamRegister);
    }

    @Override
    @Transactional
    public void registerReject(Long teamRegisterId) {
        TeamRegister teamRegister = this.findTeamRegisterById(teamRegisterId);
        teamRegisterRepository.delete(teamRegister);
    }

    @Override
    public List<TeamRegisterResponse> getListTeamRegister(Long teamId) {
        return teamRegisterRepository.getByTeamIdAndApprovalFlag(teamId, false).stream()
                .map(teamRegister -> teamRegisterMapper.toTeamRegisterResponse(teamRegister))
                .collect(Collectors.toList());
    }

    private Team findTeamById(long id) {
        return teamRepository.getReferenceById(id);
    }

    private Pitch findPitchById(long id) {
        return pitchRepository.getReferenceById(id);
    }

    private User findUserById(long id) {
        return userRepository.getReferenceById(id);
    }

    private Level findLevelById(long id) {
        return levelRepository.getReferenceById(id);
    }

    private District findDistrictById(long id) {
        return districtRepository.getReferenceById(id);
    }

    private TeamRegister findTeamRegisterById(long id) {
        return teamRegisterRepository.getReferenceById(id);
    }

    private String uploadFile(MultipartFile file, String folderName) {
        return uploadFileService.uploadImage(file, UploadFileService.FolderType.TEAM, folderName);
    }
}
