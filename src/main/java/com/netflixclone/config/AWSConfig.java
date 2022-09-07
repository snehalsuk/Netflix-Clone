package com.netflixclone.config;

import com.amazonaws.internal.StaticCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AWSConfig {

    @Value("${aws.accessKey")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;


    @Bean
    public AwsCredentialsProvider getAWSCredentials (){

    return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey,secretKey));
}
}
