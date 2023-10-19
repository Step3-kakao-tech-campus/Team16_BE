package com.daggle.animory.domain.fileserver;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.daggle.animory.common.error.exception.BadRequest400Exception;
import com.daggle.animory.common.error.exception.InternalServerError500Exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


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

    public String save(final MultipartFile file) {
        final String fileName = UUID.randomUUID().toString();

        try {
            amazonS3Client.putObject(
                new PutObjectRequest(
                    bucket,
                    fileName,
                    file.getInputStream(),
                    buildObjectMetadata(file, fileName)
                ).withCannedAcl(CannedAccessControlList.PublicRead));

        } catch (final AmazonClientException ex) {
            throw new InternalServerError500Exception("aws에 저장하는 과정에서 오류가 발생했습니다." + ex.getMessage());
        } catch (final IOException ex) {
            throw new BadRequest400Exception("올바르지 않은 파일 형식입니다.");
        }

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public void deleteAll(final List<String> savedFileUrls) {
    }



    private ObjectMetadata buildObjectMetadata(final MultipartFile file,
                                               final String fileName) throws IOException {
        final ObjectMetadata metadata = new ObjectMetadata();

        // get content type
        final Path path = Paths.get(Objects.requireNonNull(fileName));
        final String contentType = Files.probeContentType(path); // IOException here

        // set metadata
        metadata.setContentType(contentType);
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        return metadata;
    }
}
