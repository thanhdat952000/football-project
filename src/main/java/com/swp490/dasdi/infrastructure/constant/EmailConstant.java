package com.swp490.dasdi.infrastructure.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailConstant {
    public static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String PHONE_NUMBER_PATTERN = "([\\+84|84|0]+(3|5|7|8|9|1[2|6|8|9]))+([0-9]{8})\\b";
    public static final String FROM_EMAIL = "dasdi.system@gmail.com";
    public static final String EMAIL_SUBJECT_WELCOME = "DASDI | Dịch vụ bóng đá - Chào mừng";
    public static final String EMAIL_SUBJECT_OTP = "DASDI | Dịch vụ bóng đá - OTP";
    public static final String OTP_SENT = "An OTP was sent to: ";

    public static String contentWelcomeTextMail(String fullName) {
        return String.format("<p>Xin ch&agrave;o %s,</p>\n" +
                "<p><br></p>\n" +
                "<p>Ch&agrave;o mừng bạn đến với DASDI - Dịch vụ b&oacute;ng đ&aacute; chuy&ecirc;n nghiệp.</p>\n" +
                "<p>Hi vọng bạn sẽ c&oacute; những trải nghiệm tuyệt vời với hệ thống của ch&uacute;ng t&ocirc;i.</p>\n" +
                "<p><br></p>\n" +
                "<p>Tr&acirc;n trọng,</p>\n" +
                "<p>DASDI System</p>\n" +
                "<p><br></p>", fullName);
    }

    public static String contentOtpTextMail(String fullName, String otp) {
        return String.format("Xin ch&#224;o %s,<p><br></p>" +
                "<p>M&#227; OTP c&#7911;a b&#7841;n l&#224;: <i>%s</i></p>" +
                "<p>M&#227; OTP s&#7869; c&#243; th&#7901;i h&#7841;n trong 5 ph&#250;t</p>" +
                "<p><br></p><p><i>Th&#226;n m&#7871;n,</i></p>" +
                "<p><b><i>DASDI System</i></b></p><p></p>", fullName, otp);
    }

    public static String contentOtpTextPhone(String fullName, String otp) {
        return String.format("Xin chào %s\n\n" +
                "Mã OTP của bạn là: %s\n" +
                "Mã OTP sẽ có thời hạn trong 5 phút\n\n" +
                "Thân mến,\n" +
                "DASDI System", fullName, otp);
    }
}
