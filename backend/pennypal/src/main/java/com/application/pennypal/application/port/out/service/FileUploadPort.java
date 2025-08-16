package com.application.pennypal.application.port.out.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadPort {
    String uploadFile(MultipartFile file);
}
