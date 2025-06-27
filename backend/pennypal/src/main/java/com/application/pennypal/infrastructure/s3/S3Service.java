package com.application.pennypal.infrastructure.s3;

import com.application.pennypal.application.port.S3SystemPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service implements S3SystemPort {

    private final S3Client s3Client;
    private final String bucketName;

    public String uploadFile(MultipartFile file) {
        try{
            String key = "profiles/"+file.getOriginalFilename();

            PutObjectRequest putReq = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putReq, RequestBody.fromBytes(file.getBytes()));

            return s3Client.utilities().getUrl(r -> r.bucket(bucketName).key(key)).toExternalForm();

        } catch (Exception e) {
            throw new RuntimeException("File upload error");
        }
    }
}
