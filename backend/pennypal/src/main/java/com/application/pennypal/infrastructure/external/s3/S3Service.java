package com.application.pennypal.infrastructure.external.s3;

import com.application.pennypal.application.port.out.service.FileUploadPort;
import com.application.pennypal.infrastructure.exception.fileStorage.EmptyFileInfrastructureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

//@Service
//@RequiredArgsConstructor
//public class S3Service implements FileUploadPort {
//
//    private final S3Client s3Client;
//    private final String bucketName;
//
//    public String uploadFile(MultipartFile file) {
//        if(file == null || file.isEmpty()){
//            throw new EmptyFileInfrastructureException("File is empty");
//        }
//        try{
//            String key = "profiles/"+file.getOriginalFilename();
//
//            PutObjectRequest putReq = PutObjectRequest.builder()
//                    .bucket(bucketName)
//                    .key(key)
//                    .contentType(file.getContentType())
//                    .build();
//
//            s3Client.putObject(putReq, RequestBody.fromBytes(file.getBytes()));
//
//            return s3Client.utilities().getUrl(r -> r.bucket(bucketName).key(key)).toExternalForm();
//
//        } catch (Exception e) {
//            throw new RuntimeException("File upload error");
//        }
//    }
//}
