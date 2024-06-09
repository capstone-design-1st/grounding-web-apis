package org.example.first.groundingwebapis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;

import java.net.URI;
import java.util.Optional;

@Configuration
public class S3Config {

    @Value("${AWS_ACCESS_KEY}")
    private String accessKey;

    @Value("${AWS_SECRET_KEY}")
    private String secretAccessKey;

    @Value("${AWS_REGION}")
    private String region;

    @Value("${AWS_BUCKET_NAME}")
    private String bucketName;

    @Bean
    public S3Client s3Client() {

        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretAccessKey)))
                .build();
    }

    @Bean
    public String bucketName() {
        return bucketName;
    }
}