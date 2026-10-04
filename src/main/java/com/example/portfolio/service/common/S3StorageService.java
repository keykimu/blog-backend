package com.example.portfolio.service.common;

import com.example.portfolio.exception.BadRequestException;
import com.example.portfolio.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3StorageService {
    private final S3Client s3Client;
    @Value("${storage.s3.bucket-name}")
    private String bucketName;

    public String uploadFile(MultipartFile file, String projectName, String screenName){
        if(file.isEmpty()) throw new NotFoundException("ファイルが空です。");

        String contentType = file.getContentType();
        if(contentType == null || !contentType.startsWith("image/")) throw new BadRequestException("画像ファイルのみアップロード可能です");

        if(file.getSize() > 1 * 1024 * 1024) throw new BadRequestException("1MB以下の画像を指定してください");

        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName != null && originalFileName.contains(".")
                ? originalFileName.substring(originalFileName.lastIndexOf(".")) : "";
        String uniqueFileName = UUID.randomUUID() + extension;

        String objectKey = projectName + "/" + screenName + "/" + uniqueFileName;

        try{
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(),file.getSize()));

            return objectKey;
        }catch(IOException e){
            throw new RuntimeException("オブジェクトストレージへのファイルアップロードに失敗しました",e);
        }
    }
}
