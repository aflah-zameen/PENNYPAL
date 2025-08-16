package com.application.pennypal.infrastructure.external.s3;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {
    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials creds = AwsBasicCredentials.create(
                dotenv.get("AWS_ACCESS_KEY"),
                dotenv.get("AWS_SECRET_KEY")
        );

        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(creds))
                .region(Region.of(dotenv.get("AWS_REGION")))
                .build();
    }

    @Bean
    public String bucketName() {
        return dotenv.get("AWS_BUCKET_NAME");
    }
}
