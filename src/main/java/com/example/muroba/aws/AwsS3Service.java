package com.example.muroba.aws;

import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Service
@RequiredArgsConstructor
public class AwsS3Service {
    private final S3Template s3Template;
    private final S3Presigner s3Presigner;

}
