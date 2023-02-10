package com.swp490.dasdi.domain.service;

import com.swp490.dasdi.application.dto.request.pitch.BookingCreateDto;
import com.swp490.dasdi.application.dto.response.BookingResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookingService {
    Page<BookingResponse> getByUserId(long userId, int status, int page);

    Page<BookingResponse> getByPitchIdAndStatus(long pitchId, int status, int page);

    List<BookingResponse> getByMiniPitchId(long miniPitchId);

    void booking(BookingCreateDto bookingCreateDto);

    void approvalBooking(Long bookingId, Integer status);
}
