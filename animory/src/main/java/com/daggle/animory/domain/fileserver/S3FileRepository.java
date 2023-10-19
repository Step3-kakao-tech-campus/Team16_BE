package com.daggle.animory.domain.fileserver;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.daggle.animory.common.error.exception.BadRequest400Exception;
import com.daggle.animory.common.error.exception.InternalServerError500Exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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

        saveToS3(file, fileName);

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public void overwrite(final MultipartFile file, final String fileName) {
        saveToS3(file, fileName);
    }

    public void deleteAll(final List<String> savedFileUrls) {
        try{
            final DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucket)
                .withKeys(savedFileUrls.toArray(new String[0])); // S3 SDK method가 이런 수준이라 어쩔수없이 다시 array로 만드는 코드입니다.

            amazonS3Client.deleteObjects(deleteObjectsRequest);
        } catch (final Exception e) {
            // TODO: DB에 로그를 저장하고, 주기적으로 Garbage Objects를 삭제하는 로직을 추가해야합니다.
            log.error("S3 파일 삭제 중 오류 발생으로 Garbage Objects가 남아있을 수 있습니다 :{}: cause: {}", savedFileUrls, e.getMessage());
            throw e;
        }
    }

    private void saveToS3(final MultipartFile file, final String fileName) {
        try {
            amazonS3Client.putObject(
                new PutObjectRequest(
                    bucket,
                    fileName,
                    file.getInputStream(),
                    S3Util.buildObjectMetadata(file, fileName)
                ).withCannedAcl(CannedAccessControlList.PublicRead));

        } catch (final AmazonClientException ex) {
            throw new InternalServerError500Exception("aws에 저장하는 과정에서 오류가 발생했습니다." + ex.getMessage());
        } catch (final IOException ex) {
            throw new BadRequest400Exception("올바르지 않은 파일 형식입니다.");
        }
    }



}
