package com.swp490.dasdi.domain.service.impl;

import com.swp490.dasdi.application.dto.request.pitch.BookingCreateDto;
import com.swp490.dasdi.application.dto.response.BookingResponse;
import com.swp490.dasdi.domain.mapper.BookingMapper;
import com.swp490.dasdi.domain.service.BookingService;
import com.swp490.dasdi.infrastructure.entity.Booking;
import com.swp490.dasdi.infrastructure.entity.MiniPitch;
import com.swp490.dasdi.infrastructure.entity.User;
import com.swp490.dasdi.infrastructure.reposiotory.BookingRepository;
import com.swp490.dasdi.infrastructure.reposiotory.MiniPitchRepository;
import com.swp490.dasdi.infrastructure.reposiotory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final MiniPitchRepository miniPitchRepository;
    private final BookingMapper bookingMapper;
    private final Integer CANCEL = -1;
    private final Integer WAITING_FOR_APPROVE = 0;
    private final Integer IN_PROGRESS = 1;
    private final Integer DONE = 2;

    @Override
    public Page<BookingResponse> getByUserId(long userId, int status, int page) {
        Page<Booking> bookings = bookingRepository.findByUserIdAndStatus(userId, status, PageRequest.of(page, 6));
        return new PageImpl<>(bookings.stream()
                .map(booking -> bookingMapper.toBookingResponse(booking))
                .collect(Collectors.toList()), PageRequest.of(page, 6), bookings.getTotalElements());
    }

    @Override
    public Page<BookingResponse> getByPitchIdAndStatus(long pitchId, int status, int page) {
        Page<Booking> bookings = bookingRepository.findByPitchIdAndStatus(pitchId, status, PageRequest.of(page, 6));
        return new PageImpl<>(bookings.stream()
                .map(booking -> bookingMapper.toBookingResponse(booking))
                .collect(Collectors.toList()), PageRequest.of(page, 6), bookings.getTotalElements());
    }

    @Override
    public List<BookingResponse> getByMiniPitchId(long miniPitchId) {
        List<Booking> bookings = bookingRepository.findByMiniPitchId(miniPitchId, List.of(1, 2));
        return bookings.stream()
                .map(booking -> bookingMapper.toBookingResponse(booking))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void booking(BookingCreateDto bookingCreateDto) {
        User user = this.findUserById(bookingCreateDto.getUserId());
        MiniPitch miniPitch = this.findMiniPitchById(bookingCreateDto.getMiniPitchId());
        Booking booking = Booking.builder()
                .timeStart(LocalDateTime.parse(bookingCreateDto.getTimeStart(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")))
                .timeEnd(LocalDateTime.parse(bookingCreateDto.getTimeEnd(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")))
                .note(bookingCreateDto.getNote())
                .payment(bookingCreateDto.getPayment())
                .price(bookingCreateDto.getPrice())
                .status(this.checkBookingStatus(user, miniPitch, bookingCreateDto.getPayment()) ? IN_PROGRESS : WAITING_FOR_APPROVE)
                .user(user)
                .miniPitch(miniPitch)
                .build();
        bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public void approvalBooking(Long bookingId, Integer status) {
        Booking booking = bookingRepository.getReferenceById(bookingId);
        if (status == 0) {
            booking.setStatus(CANCEL);
        } else if (status == 1) {
            booking.setStatus(IN_PROGRESS);
        } else if (status == 2) {
            booking.setStatus(DONE);
        }
        bookingRepository.save(booking);
    }

    private MiniPitch findMiniPitchById(long id) {
        return miniPitchRepository.getReferenceById(id);
    }

    private User findUserById(long id) {
        return userRepository.getReferenceById(id);
    }

    private boolean checkBookingStatus(User user, MiniPitch miniPitch, int payment) {
        return miniPitch.getPitch().getOwner().equals(user) || payment == 1;
    }
}
