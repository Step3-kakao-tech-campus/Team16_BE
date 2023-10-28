package com.daggle.animory.domain.fileserver;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

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
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider
                    .create(AwsBasicCredentials
                        .create(accessKey, secretKey)))
                .build();
    }


//    @Bean
//    public AmazonS3Client amazonS3Client() {
//
//        final BasicAWSCredentials awsCredentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
//        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
//            .withRegion(this.region)
//            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
//            .build();
//    }

}
