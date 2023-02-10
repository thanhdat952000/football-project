package com.swp490.dasdi.application.controller;

import com.swp490.dasdi.application.dto.request.pitch.BookingCreateDto;
import com.swp490.dasdi.application.dto.response.BookingResponse;
import com.swp490.dasdi.application.dto.response.HttpResponse;
import com.swp490.dasdi.domain.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<Page<BookingResponse>> getByUserId(@RequestParam long userId, @RequestParam int status, @RequestParam(required = false, defaultValue = "0") int page) {
        Page<BookingResponse> bookings = bookingService.getByUserId(userId, status, page);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/pitch")
    public ResponseEntity<Page<BookingResponse>> getByPitchId(@RequestParam long pitchId, @RequestParam int status, @RequestParam(required = false, defaultValue = "0") int page) {
        Page<BookingResponse> bookings = bookingService.getByPitchIdAndStatus(pitchId, status, page);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/mini-pitch")
    public ResponseEntity<List<BookingResponse>> getByMiniPitch(@RequestParam long miniPitchId) {
        List<BookingResponse> bookings = bookingService.getByMiniPitchId(miniPitchId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/approval")
    public ResponseEntity<HttpResponse> cancelBooking(@RequestParam Long bookingId, @RequestParam Integer status) {
        bookingService.approvalBooking(bookingId, status);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "Change status of booking successfully!");
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpResponse> booking(@RequestBody BookingCreateDto bookingCreateDto) {
        bookingService.booking(bookingCreateDto);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, "Update pitch successfully!");
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    private HttpResponse setHttpResponse(HttpStatus httpStatus, String message) {
        return HttpResponse.builder().timeStamp(LocalDateTime.now()).httpStatusCode(httpStatus.value()).httpStatus(httpStatus).reason(httpStatus.getReasonPhrase().toUpperCase()).message(message).build();
    }
}
