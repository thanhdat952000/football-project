package com.swp490.dasdi.application.controller;

import com.swp490.dasdi.application.dto.response.CityResponse;
import com.swp490.dasdi.domain.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/list")
    public ResponseEntity<List<CityResponse>> getAll() {
        return new ResponseEntity<>(cityService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getById(@PathVariable long id) {
        return new ResponseEntity<>(cityService.getById(id), HttpStatus.OK);
    }
}
