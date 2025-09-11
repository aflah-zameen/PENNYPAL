package com.application.pennypal.infrastructure.external.cloudinary;

import com.application.pennypal.application.port.out.service.FileUploadPort;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudinaryService implements FileUploadPort {
    private final Cloudinary cloudinary;
    @Override
    public String uploadFile(MultipartFile file) {
        try {
            String publicId = "pennypal/" + UUID.randomUUID();

            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "public_id", publicId,
                            "resource_type", "auto"
                    )
            );

            // Cloudinary returns "secure_url" (https://res.cloudinary.com/...)
            return (String) uploadResult.get("secure_url");

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to Cloudinary", e);
        }
    }
}
