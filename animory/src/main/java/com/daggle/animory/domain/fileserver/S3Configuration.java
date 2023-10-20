package com.daggle.animory.domain.fileserver;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Configuration {

    private final String accessKey;
    private final String secretKey;
    private final String region;

    public S3Configuration(@Value("${cloud.aws.credentials.access-key}") final String accessKey,
                           @Value("${cloud.aws.credentials.secret-key}") final String secretKey,
                           @Value("${cloud.aws.region.static}") final String region
    ) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = region;
    }

    @Bean
    public AmazonS3Client amazonS3Client() {
        final BasicAWSCredentials awsCredentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
            .withRegion(this.region)
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .build();
    }

}
