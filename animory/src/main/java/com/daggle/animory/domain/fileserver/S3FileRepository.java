package com.daggle.animory.domain.fileserver;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.daggle.animory.common.error.exception.BadRequest400;
import com.daggle.animory.common.error.exception.InternalServerError500;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;


@Slf4j
@Repository
public class S3FileRepository {

    private final AmazonS3Client amazonS3Client;
    private final String bucket;

    public S3FileRepository(@Value("${cloud.aws.s3.bucket}") final String bucket,
                            @Autowired final AmazonS3Client amazonS3Client) {
        this.bucket = bucket;
        this.amazonS3Client = amazonS3Client;
    }

    public URL save(final MultipartFile file) {
        final String fileName = file.getOriginalFilename();
        final Path path = Paths.get(Objects.requireNonNull(fileName));
        try {
            final String contentType = Files.probeContentType(path);
            final ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            amazonS3Client.putObject(
                new PutObjectRequest(
                    bucket, fileName, file.getInputStream(), metadata
                ).withCannedAcl(CannedAccessControlList.PublicRead));

        } catch (final AmazonClientException ex) {
            throw new InternalServerError500("aws에 저장하는 과정에서 오류가 발생했습니다." + ex.getMessage());
        } catch (final IOException ex) {
            throw new BadRequest400("올바르지 않은 파일 형식입니다.");
        }

        return amazonS3Client.getUrl(bucket, fileName);
    }

    public void deleteAll(final List<URL> savedFileUrls) {
    }
}
