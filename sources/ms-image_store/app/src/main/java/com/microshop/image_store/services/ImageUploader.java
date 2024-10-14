package com.microshop.image_store.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Service
public class ImageUploader {

    private final S3Client s3Client;

    @Autowired
    public ImageUploader(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    /**
     * Uploads an JPEG image to the S3
     *
     * @param image_code - is the code of the image
     * @param image_bytes - is the binary of the image (must be jpeg)
     * @return
     */
    public PutObjectResponse uploadJPEGImage(Integer image_code, byte[] image_bytes) {
        var JPEG_CONTENT_TYPE = "image/jpeg";

        var por =
                PutObjectRequest.builder()
                        .bucket("images")
                        .key(image_code.toString())
                        .contentType(JPEG_CONTENT_TYPE)
                        .build();

        var response = s3Client.putObject(por, RequestBody.fromBytes(image_bytes));
        return response;
    }
}
