package com.swp490.dasdi.domain.service;

import com.swp490.dasdi.application.dto.request.pitch.BookingCreateDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final BookingService bookingService;
    private final String VNP_TMN_CODE = "HUAPZV6V";
    private final String VNP_HASH_SECRET = "QLKZJRSICLOSWRLXBNOCORWACXHJAUSZ";
    private final String VNP_RETURN_URL = "https://dasdi.one/user/booking-history";

    @Transactional
    public String getPaymentURLVnPay(BookingCreateDto bookingCreateDto, HttpServletRequest request) throws UnsupportedEncodingException {
        bookingService.booking(bookingCreateDto);

        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", VNP_TMN_CODE);
        vnpParams.put("vnp_Amount", String.valueOf(bookingCreateDto.getPrice()).concat("00000"));
        vnpParams.put("vnp_CurrCode", "VND");
        vnpParams.put("vnp_BankCode", "NCB");
        vnpParams.put("vnp_TxnRef", RandomStringUtils.randomNumeric(8));
        vnpParams.put("vnp_OrderInfo", bookingCreateDto.getNote());
        vnpParams.put("vnp_OrderType", "topup");
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", VNP_RETURN_URL);
        vnpParams.put("vnp_IpAddr", request.getRemoteAddr());

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        cld.add(Calendar.HOUR, 7);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(cld.getTime());
        vnpParams.put("vnp_CreateDate", vnpCreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnpExpireDate = formatter.format(cld.getTime());
        vnpParams.put("vnp_ExpireDate", vnpExpireDate);


        List fieldNames = new ArrayList(vnpParams.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnpParams.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append("=");
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append("=");
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append("&");
                    hashData.append("&");
                }
            }
        }
        String queryUrl = query.toString();
        String vnpHashSecret = VNP_HASH_SECRET;
        String vnpSecureHash = new HmacUtils("HmacSHA512", vnpHashSecret).hmacHex(hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String vnpPayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
        String paymentUrl = vnpPayUrl + "?" + queryUrl;
        return paymentUrl;
    }
}
