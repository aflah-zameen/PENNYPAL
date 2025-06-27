package com.application.pennypal.application.port;

import org.springframework.web.multipart.MultipartFile;

public interface S3SystemPort {
    String uploadFile(MultipartFile file);
}
