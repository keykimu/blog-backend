package com.example.portfolio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class S3Config {
    @Configuration
    @Profile({"local", "test"})
    static class MinioS3Config {
        @Value("${minio.endpoint}")
        private String endpoint;

        @Value("${minio.access-key}")
        private String accessKey;

        @Value("${minio.secret-key}")
        private String secretKey;

        @Bean
        public S3Client minioS3Client(){
            return S3Client.builder()
                    .endpointOverride(URI.create(endpoint))
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create(accessKey,secretKey)
                    ))
                    .region(Region.AP_NORTHEAST_1)
                    .forcePathStyle(true)
                    .build();
        }
    }

    @Profile("prod")
    @Configuration
    static class AwsS3Config {
        @Bean
        public S3Client awsS3Client(@Value("${storage.s3.region}") String region) {
            return S3Client.builder()
                    .region(Region.of(region))
                    .build();
        }
    }
}
