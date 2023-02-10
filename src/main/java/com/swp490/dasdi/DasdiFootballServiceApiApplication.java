package com.swp490.dasdi;

import com.cloudinary.Cloudinary;
import com.cloudinary.SingletonManager;
import com.cloudinary.utils.ObjectUtils;
import com.swp490.dasdi.infrastructure.configuration.AppOAuth2Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(AppOAuth2Properties.class)
public class DasdiFootballServiceApiApplication {

    public static void main(String[] args) {
        // Set Cloudinary instance
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dtj84wylh",
                "api_key", "478256537368494",
                "api_secret", "K3ExhhngWJU_f6pA-s5duppxdSY",
                "secure", true));
        SingletonManager manager = new SingletonManager();
        manager.setCloudinary(cloudinary);
        manager.init();

        // Application run
        SpringApplication.run(DasdiFootballServiceApiApplication.class, args);
    }

}
