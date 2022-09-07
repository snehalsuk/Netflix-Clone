package com.netflixclone.accessor;

import com.amazonaws.auth.AWSCredentialsProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class S3Accessor {

private final String bucketName="netflix-sps";

    @Autowired
    private AWSCredentialsProvider awsCredentialsProvider;

public String getPresignedUrl(final String path,final int durationInSecond){

    S3Presigner presigner = S3Presigner
            .builder()
            .region(Region.AP_SOUTH_1)
            .credentialsProvider(awsCredentialsProvider)
            .build();

    GetObjectRequest getObjectRequest = GetObjectRequest
            .builder()
            .key(path)
            .bucket(bucketName)
            .build();

    GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest
            .builder()
            .signatureDuration(Duration.ofSeconds(durationInSecond))
            .getObjectRequest(getObjectRequest)
            .build();
    PresignedGetObjectRequest request = presigner.presignGetObject(getObjectPresignRequest);
    return request.url().toString();
}


}
