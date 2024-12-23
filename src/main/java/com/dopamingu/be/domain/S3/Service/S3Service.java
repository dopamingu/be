package com.dopamingu.be.domain.S3.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    /** S3에 이미지 업로드 하기 */
    public String uploadImage(MultipartFile image) throws IOException {
        String extension = getImageExtension(image);
        String fileName =
                UUID.randomUUID() + "_" + image.getOriginalFilename() + extension; // 고유한 파일 이름 생성

        // 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(image.getContentType());
        metadata.setContentLength(image.getSize());

        // S3에 파일 업로드 요청 생성
        PutObjectRequest putObjectRequest =
                new PutObjectRequest(bucket, fileName, image.getInputStream(), metadata);

        // S3에 파일 업로드
        amazonS3.putObject(putObjectRequest);

        return getPublicUrl(fileName);
    }

    private String getPublicUrl(String fileName) {
        return String.format(
                "https://%s.s3.%s.amazonaws.com/%s", bucket, amazonS3.getRegionName(), fileName);
    }

    private String getImageExtension(MultipartFile image) {
        String extension = "";
        String originalFilename = image.getOriginalFilename();

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        }
        return extension;
    }
}