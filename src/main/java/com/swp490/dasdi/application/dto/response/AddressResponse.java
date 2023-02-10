package com.swp490.dasdi.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {
    private String detail;
    private String phoneNumber;
    private String facebook;
    private String mail;
    private String latitude;
    private String longitude;
    private String district;
    private String city;
    private Long districtId;
    private Long cityId;
}
