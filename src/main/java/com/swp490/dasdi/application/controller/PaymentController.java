package com.swp490.dasdi.application.controller;

import com.swp490.dasdi.application.dto.request.pitch.BookingCreateDto;
import com.swp490.dasdi.application.dto.response.HttpResponse;
import com.swp490.dasdi.domain.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/createURLVnPay")
    public ResponseEntity<HttpResponse> createPaymentURLVnPay(@RequestBody BookingCreateDto bookingCreateDto, HttpServletRequest request) throws UnsupportedEncodingException {
        String paymentVNPayUrl =  paymentService.getPaymentURLVnPay(bookingCreateDto, request);
        HttpResponse httpResponse = this.setHttpResponse(HttpStatus.OK, paymentVNPayUrl);
        return new ResponseEntity<>(httpResponse, HttpStatus.OK);
    }

    private HttpResponse setHttpResponse(HttpStatus httpStatus, String message) {
        return HttpResponse.builder()
                .timeStamp(LocalDateTime.now())
                .httpStatusCode(httpStatus.value())
                .httpStatus(httpStatus)
                .reason(httpStatus.getReasonPhrase().toUpperCase())
                .message(message)
                .build();
    }
}
