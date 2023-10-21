package com.daggle.animory.domain.fileserver;

import com.daggle.animory.domain.fileserver.exception.AmazonS3SaveError;
import com.daggle.animory.domain.fileserver.exception.InvalidFileTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Slf4j
@Repository
public class S3FileRepository {

    private final S3Client s3Client;
    private final String bucket;

    public S3FileRepository(@Value("${cloud.aws.s3.bucket}") final String bucket,
                            @Autowired final S3Client s3Client) {
        this.bucket = bucket;
        this.s3Client = s3Client;
    }

    public String save(final MultipartFile file) {
        final String fileName = UUID.randomUUID().toString();

        saveToS3(file, fileName);

        return s3Client
            .utilities()
            .getUrl(builder -> builder
                .bucket(bucket)
                .key(fileName))
            .toString();
    }

    public void overwrite(final MultipartFile file, final String fileName) {
        saveToS3(file, fileName);
    }

    public void deleteAll(final List<String> savedFileUrls) {
        try {
            s3Client.deleteObjects(deleteObjectRequestBuilder -> deleteObjectRequestBuilder
                .bucket(bucket)
                .delete(objectIdentifiersBuilder -> objectIdentifiersBuilder
                    .objects(savedFileUrls.stream()
                        .map(savedFileUrl -> ObjectIdentifier.builder()
                            .key(savedFileUrl)
                            .build())
                        .toArray(ObjectIdentifier[]::new))
                    .build())
                .build()
            );
        } catch (final SdkException e) {
            // TODO: DB에 로그를 저장하고, 주기적으로 Garbage Objects를 삭제하는 로직을 추가해야합니다.
            log.error("S3 파일 삭제 중 오류 발생으로 Garbage Objects가 남아있을 수 있습니다 :{}: cause: {}", savedFileUrls, e.getMessage());
            throw e;
        }
    }


    private void saveToS3(final MultipartFile file, final String fileName) {
        try {
            s3Client.putObject(builder -> builder
                    .bucket(bucket)
                    .key(fileName)
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build(),
                RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );

        } catch (final SdkException ex) {
            throw new AmazonS3SaveError(ex.getMessage());
        } catch (final IOException ex) {
            throw new InvalidFileTypeException();
        }
    }
}
