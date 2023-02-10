package com.swp490.dasdi.domain.service.impl;

import com.swp490.dasdi.application.dto.request.pitch.MiniPitchCreateDto;
import com.swp490.dasdi.application.dto.request.pitch.PitchCreateDto;
import com.swp490.dasdi.application.dto.request.pitch.PitchUpdateDto;
import com.swp490.dasdi.application.dto.request.pitch.ReviewPitchCreateDto;
import com.swp490.dasdi.application.dto.response.CostRangeResponse;
import com.swp490.dasdi.application.dto.response.PitchResponse;
import com.swp490.dasdi.application.dto.response.ReviewStarResponse;
import com.swp490.dasdi.application.dto.response.UserResponse;
import com.swp490.dasdi.domain.mapper.PitchMapper;
import com.swp490.dasdi.domain.mapper.UserMapper;
import com.swp490.dasdi.domain.service.PitchService;
import com.swp490.dasdi.domain.service.UploadFileService;
import com.swp490.dasdi.infrastructure.entity.*;
import com.swp490.dasdi.infrastructure.entity.enumeration.DayWorkingType;
import com.swp490.dasdi.infrastructure.reposiotory.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PitchServiceImpl implements PitchService {

    private final PitchRepository pitchRepository;
    private final ReviewPitchRepository reviewPitchRepository;
    private final DistrictRepository districtRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PitchTypeRepository pitchTypeRepository;
    private final PitchMapper pitchMapper;
    private final UserMapper userMapper;
    private final UploadFileService uploadFileService;
    private final List<Long> PITCH_TYPE_IDs_DEFAULT = List.of(1L, 2L, 3L, 4L);

    private final int CANCEL_APPROVE = -1;
    private final int INACTIVE = 0;
    private final int WAITING_FOR_APPROVE = 1;
    private final int ACTIVE = 2;
    private final int PAGE_SIZE = 6;

    @Override
    public List<PitchResponse> getAll(int page) {
        List<Pitch> pitches = pitchRepository.findAll(PageRequest.of(page, PAGE_SIZE)).getContent();
        List<PitchResponse> pitchResponses = pitches.stream()
                .map(pitch -> pitchMapper.toPitchResponse(pitch))
                .collect(Collectors.toList());
        pitchResponses.forEach(pitch -> setPropertyPitch(pitches, pitch));
        return pitchResponses;
    }

    @Override
    public List<PitchResponse> filterByCondition(Long cityId, Long districtId, List<Long> pitchTypeIds, Integer minPrice, Integer maxPrice, String keyword, String sortBy, Integer page) {
        List<Pitch> pitches = pitchRepository.filterByCondition(cityId, districtId, Objects.nonNull(pitchTypeIds) ? pitchTypeIds : PITCH_TYPE_IDs_DEFAULT, minPrice, maxPrice, keyword, ACTIVE, PageRequest.of(page, PAGE_SIZE, Sort.by(sortBy)));

        List<PitchResponse> pitchResponses = pitches.stream()
                .map(pitch -> pitchMapper.toPitchResponse(pitch))
                .collect(Collectors.toList());

        pitchResponses.forEach(pitch -> setPropertyPitch(pitches, pitch));
        return pitchResponses;
    }

    @Override
    public PitchResponse getById(long id) {
        Pitch pitch = this.findPitchById(id);
        PitchResponse pitchResponse = pitchMapper.toPitchResponse(pitch);
        // set cost range
        List<Cost> costs = pitch.getMiniPitches().stream().flatMap(miniPitch -> miniPitch.getCosts().stream()).collect(Collectors.toList());
        pitchResponse.setCostRange(CostRangeResponse.builder()
                .minPrice(costs.stream()
                        .mapToInt(Cost::getPrice)
                        .min()
                        .orElse(0))
                .maxPrice(costs.stream()
                        .mapToInt(Cost::getPrice)
                        .max()
                        .orElse(500))
                .build());

        // set review star
        List<ReviewPitch> reviewPitches = pitch.getReviewPitches();
        ReviewStarResponse reviewStarResponse = ReviewStarResponse.builder()
                .avgStar(Math.round(reviewPitches.stream()
                        .mapToInt(ReviewPitch::getStar)
                        .average()
                        .orElse(5)))
                .totalReview(reviewPitches.size())
                .build();
        pitchResponse.setReviewStar(reviewStarResponse);

        // set pitch type name
        List<String> pitchTypes = pitch.getMiniPitches().stream().map(miniPitch -> miniPitch.getPitchType().getName()).collect(Collectors.toList());
        pitchResponse.setPitchTypes(pitchTypes);
        return pitchResponse;
    }

    @Override
    @Transactional
    public void changeStatus(long id, int status) {
        Pitch pitch = this.findPitchById(id);
        pitch.setStatus(status);
        pitchRepository.save(pitch);
    }

    @Override
    @Transactional
    public void changeStatusPitch(Long pitchId, Integer status) {
        Pitch pitch = pitchRepository.getReferenceById(pitchId);
        if (status == 0) {
            pitch.setStatus(CANCEL_APPROVE);
        } else if (status == 1) {
            pitch.setStatus(ACTIVE);
        } else if (status == 2) {
            pitch.setStatus(INACTIVE);
        }
        pitchRepository.save(pitch);
    }


    @Override
    @Transactional
    public void reviewPitch(ReviewPitchCreateDto reviewPitchCreateDto) {
        ReviewPitch reviewPitch = ReviewPitch.builder()
                .star(reviewPitchCreateDto.getStar())
                .content(reviewPitchCreateDto.getContent())
                .createDate(LocalDateTime.now())
                .pitch(this.findPitchById(reviewPitchCreateDto.getPitchId()))
                .user(this.findUserById(reviewPitchCreateDto.getUserId()))
                .build();
        reviewPitchRepository.save(reviewPitch);
    }

    @Override
    public List<PitchResponse> getByOwnerId(long ownerId) {
        List<Pitch> pitches = pitchRepository.findByOwnerId(ownerId);
        List<PitchResponse> pitchResponses = pitches.stream()
                .map(pitch -> pitchMapper.toPitchResponse(pitch))
                .collect(Collectors.toList());
        pitchResponses.forEach(pitch -> setPropertyPitch(pitches, pitch));
        return pitchResponses;
    }

    @Override
    @Transactional
    public UserResponse create(PitchCreateDto pitchCreateDto) {
        User owner = this.findUserById(pitchCreateDto.getOwnerId());
        if (owner.getRole() == roleRepository.getReferenceById(2L) || owner.getRole() == roleRepository.getReferenceById(4L)) {
            owner.setRole(roleRepository.getReferenceById(4L));
        } else {
            owner.setRole(roleRepository.getReferenceById(3L));
        }

        District district = this.findDistrictById(pitchCreateDto.getDistrictId());

        Address address = Address.builder()
                .detail(pitchCreateDto.getAddressDetail())
                .phoneNumber(pitchCreateDto.getPhoneNumber())
                .facebook(pitchCreateDto.getFacebook())
                .mail(pitchCreateDto.getMail())
                .latitude(pitchCreateDto.getLatitude())
                .longitude(pitchCreateDto.getLongitude())
                .district(district)
                .build();

        String logoUrl = this.uploadFile(pitchCreateDto.getLogo(), pitchCreateDto.getName());

        List<Image> images = pitchCreateDto.getImages().stream()
                .map(file -> Image.builder()
                        .url(this.uploadFile(file, file.getName()))
                        .build())
                .collect(Collectors.toList());

        List<MiniPitch> miniPitches = new ArrayList<>();
        for (MiniPitchCreateDto miniPitchCreate : pitchCreateDto.getMiniPitches()) {
            List<Cost> costs = miniPitchCreate.getCosts().stream()
                    .map(cost -> Cost.builder()
                            .hourStart(LocalTime.parse(cost.getHourStart()))
                            .hourEnd(LocalTime.parse(cost.getHourEnd()))
                            .dayWorkingType(DayWorkingType.values()[cost.getDayWorkingType()])
                            .price(cost.getPrice())
                            .build())
                    .collect(Collectors.toList());
            MiniPitch miniPitch = MiniPitch.builder()
                    .name(this.findPitchTypeById(miniPitchCreate.getPitchTypeId()).getName())
                    .quantity(miniPitchCreate.getQuantity())
                    .status(true)
                    .pitchType(this.findPitchTypeById(miniPitchCreate.getPitchTypeId()))
                    .costs(new ArrayList<>())
                    .build();
            miniPitch.getCosts().addAll(costs);
            costs.forEach(cost -> cost.setMiniPitch(miniPitch));
            miniPitches.add(miniPitch);
        }

        Pitch pitch = Pitch.builder()
                .name(pitchCreateDto.getName())
                .description(pitchCreateDto.getDescription())
                .logoUrl(logoUrl)
                .hourStart(LocalTime.parse(pitchCreateDto.getHourStart()))
                .hourEnd(LocalTime.parse(pitchCreateDto.getHourEnd()))
                .status(WAITING_FOR_APPROVE)
                .createdDate(LocalDateTime.now())
                .address(address)
                .owner(owner)
                .miniPitches(new ArrayList<>())
                .images(new ArrayList<>())
                .build();

        pitch.getMiniPitches().addAll(miniPitches);
        pitch.getImages().addAll(images);
        miniPitches.forEach(miniPitch -> miniPitch.setPitch(pitch));
        images.forEach(image -> image.setPitch(pitch));

        pitchRepository.save(pitch);
        return userMapper.toUserResponse(userRepository.save(owner));
    }

    @Override
    @Transactional
    public void update(long id, PitchUpdateDto pitchUpdateDto) {
        Pitch pitch = this.findPitchById(pitchUpdateDto.getId());

        if (Objects.nonNull(pitchUpdateDto.getName())) {
            pitch.setName(pitchUpdateDto.getName());
        }
        if (Objects.nonNull(pitchUpdateDto.getDescription())) {
            pitch.setDescription(pitchUpdateDto.getDescription());
        }
        if (Objects.nonNull(pitchUpdateDto.getHourStart())) {
            pitch.setHourStart(LocalTime.parse(pitchUpdateDto.getHourStart()));
        }
        if (Objects.nonNull(pitchUpdateDto.getHourEnd())) {
            pitch.setHourEnd(LocalTime.parse(pitchUpdateDto.getHourEnd()));
        }

        //update address
        Address address = pitch.getAddress();
        if (Objects.nonNull(pitchUpdateDto.getAddressDetail())) {
            address.setDetail(pitchUpdateDto.getAddressDetail());
        }
        if (Objects.nonNull(pitchUpdateDto.getPhoneNumber())) {
            address.setPhoneNumber(pitchUpdateDto.getPhoneNumber());
        }
        if (Objects.nonNull(pitchUpdateDto.getFacebook())) {
            address.setFacebook(pitchUpdateDto.getFacebook());
        }
        if (Objects.nonNull(pitchUpdateDto.getMail())) {
            address.setMail(pitchUpdateDto.getMail());
        }
        if (Objects.nonNull(pitchUpdateDto.getDistrictId())) {
            address.setDistrict(this.findDistrictById(pitchUpdateDto.getDistrictId()));
        }
        pitch.setAddress(address);

        //update logo
        if (Objects.nonNull(pitchUpdateDto.getLogo())) {
            pitch.setLogoUrl(this.uploadFile(pitchUpdateDto.getLogo(), pitchUpdateDto.getName()));
        }

        //update images
        if (Objects.nonNull(pitchUpdateDto.getImages())) {
            List<Image> images = pitchUpdateDto.getImages().stream()
                    .map(file -> Image.builder()
                            .url(this.uploadFile(file, file.getName()))
                            .build())
                    .collect(Collectors.toList());
            pitch.setImages(images);
        }

        pitch.setUpdatedDate(LocalDateTime.now());

        pitchRepository.save(pitch);
    }

    @Override
    public Page<PitchResponse> getByStatus(int status, Integer page) {
        Page<Pitch> pitches = pitchRepository.findByStatus(status, PageRequest.of(page, PAGE_SIZE));
        List<PitchResponse> pitchResponses = pitches.stream()
                .map(pitch -> pitchMapper.toPitchResponse(pitch))
                .collect(Collectors.toList());
        pitchResponses.forEach(pitch -> setPropertyPitch(pitches.getContent(), pitch));
        return new PageImpl<>(pitchResponses, PageRequest.of(page, PAGE_SIZE), pitches.getTotalElements());
    }

    @Override
    @Transactional
    public void registerApproval(long pitchId) {
        Pitch pitch = this.findPitchById(pitchId);
        pitch.setStatus(ACTIVE);
        pitchRepository.save(pitch);
    }

    @Override
    @Transactional
    public void registerReject(long pitchId) {
        Pitch pitch = this.findPitchById(pitchId);
        pitch.setStatus(INACTIVE);
        pitchRepository.save(pitch);
    }

    private Pitch findPitchById(long id) {
        return pitchRepository.getReferenceById(id);
    }

    private District findDistrictById(long id) {
        return districtRepository.getReferenceById(id);
    }

    private User findUserById(long id) {
        return userRepository.getReferenceById(id);
    }

    private PitchType findPitchTypeById(long id) {
        return pitchTypeRepository.getReferenceById(id);
    }

    private List<Cost> getCostRangeByPitchId(List<Pitch> pitches, long pitchId) {
        Pitch pitch = pitches.stream().filter(p -> pitchId == p.getId()).findFirst().get();
        return pitch.getMiniPitches().stream()
                .flatMap(miniPitch -> miniPitch.getCosts().stream())
                .collect(Collectors.toList());
    }

    private List<ReviewPitch> getReviewByPitchId(List<Pitch> pitches, long pitchId) {
        Pitch pitch = pitches.stream().filter(p -> pitchId == p.getId()).findFirst().get();
        return pitch.getReviewPitches();
    }

    private List<String> getPitchTypeNameByPitchId(List<Pitch> pitches, long pitchId) {
        Pitch pitch = pitches.stream().filter(p -> pitchId == p.getId()).findFirst().get();
        return pitch.getMiniPitches().stream()
                .map(miniPitch -> miniPitch.getPitchType().getName())
                .collect(Collectors.toList());
    }

    private void setPropertyPitch(List<Pitch> pitches, PitchResponse pitch) {
        // set cost range
        List<Cost> costs = this.getCostRangeByPitchId(pitches, pitch.getId());
        pitch.setCostRange(CostRangeResponse.builder()
                .minPrice(costs.stream()
                        .mapToInt(Cost::getPrice)
                        .min()
                        .orElse(0))
                .maxPrice(costs.stream()
                        .mapToInt(Cost::getPrice)
                        .max()
                        .orElse(500))
                .build());

        // set review star
        List<ReviewPitch> reviewPitches = this.getReviewByPitchId(pitches, pitch.getId());
        ReviewStarResponse reviewStarResponse = ReviewStarResponse.builder()
                .avgStar(Math.round(reviewPitches.stream()
                        .mapToInt(ReviewPitch::getStar)
                        .average()
                        .orElse(5)))
                .totalReview(reviewPitches.size())
                .build();
        pitch.setReviewStar(reviewStarResponse);

        // set pitch type name
        List<String> pitchTypes = this.getPitchTypeNameByPitchId(pitches, pitch.getId());
        pitch.setPitchTypes(pitchTypes);
    }

    private String uploadFile(MultipartFile file, String folderName) {
        return uploadFileService.uploadImage(file, UploadFileService.FolderType.PITCH, folderName);
    }
}
