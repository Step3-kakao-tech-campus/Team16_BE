package com.daggle.animory.domain.fileserver;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.daggle.animory.common.error.exception.BadRequest400;
import com.daggle.animory.common.error.exception.InternalServerError500;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Slf4j
@Repository
public class S3FileRepository{

    private final String accessKey;
    private final String secretKey;
    private final String region;
    private final String bucket;

    private final AmazonS3 amazonS3Client;
    public S3FileRepository(@Value("${cloud.aws.credentials.access-key}")final String accessKey,
                            @Value("${cloud.aws.credentials.secret-key}")final String secretKey,
                            @Value("${cloud.aws.region.static}")final String region,
                            @Value("${cloud.aws.s3.bucket}")final String bucket
    ){
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = region;
        this.bucket = bucket;
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.amazonS3Client = AmazonS3ClientBuilder.standard()
                .withRegion(this.region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }


    public String getUrl(String path) {
        return amazonS3Client.getUrl(bucket, path).toString();
    }


    public URL save(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Path path = Paths.get(fileName);
        try{
            String contentType = Files.probeContentType(path);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            amazonS3Client.putObject(
                    new PutObjectRequest(
                            bucket, fileName, file.getInputStream(),metadata
                    ).withCannedAcl(CannedAccessControlList.PublicRead));

        }
        catch(SdkClientException ex){
            throw new InternalServerError500("aws에 저장하는 과정에서 오류가 발생했습니다."+ex.getMessage());
        }
        catch(IOException ex){
            throw new BadRequest400("올바르지 않은 파일 형식입니다.");
        }

        // just for checking
        ListObjectsV2Result listObjectsV2Result = amazonS3Client.listObjectsV2(bucket);
        List<S3ObjectSummary> objectSummaries = listObjectsV2Result.getObjectSummaries();

        for(S3ObjectSummary object: objectSummaries){
            System.out.println("object = " + object.toString());
        }

        return amazonS3Client.getUrl(bucket, fileName);
    }
}
