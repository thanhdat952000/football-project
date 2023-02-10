package com.swp490.dasdi.domain.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import com.swp490.dasdi.domain.exception.NotAnImageFileException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static org.springframework.http.MediaType.*;

@Service
public class UploadFileService {

    @Getter
    @AllArgsConstructor
    public enum FolderType {
        USER("User"),
        PITCH("Pitch"),
        TEAM("Team");

        private String value;
    }

    private final Cloudinary cloudinary = Singleton.getCloudinary();

    @Transactional
    public String uploadImage(MultipartFile file, FolderType folderType, String folderName) {
        if (!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(file.getContentType())) {
            throw new NotAnImageFileException(file.getOriginalFilename() + "is not an image file!");
        }
        Map params = ObjectUtils.asMap(
                "public_id", file.getOriginalFilename(),
                "folder", String.format("DASDI/%s/%s", folderType.getValue(), folderName),
                "overwrite", true
        );
        Map uploadResult = null;
        try {
            uploadResult = cloudinary.uploader().upload(file.getBytes(), params);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return uploadResult.get("url").toString();
    }
}
