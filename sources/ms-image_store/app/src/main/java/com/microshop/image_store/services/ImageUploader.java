package com.microshop.image_store.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import com.microshop.image_store.images.ImageSpec;

@Service
public class ImageUploader {
    private final String bucketName = "microshop.product-images";
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
    public void uploadJPEGImage(Integer image_code, byte[] image_bytes) {

        var JPEG_CONTENT_TYPE = "image/jpeg";
        PutObjectRequest por =
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(image_code.toString())
                        .contentType(JPEG_CONTENT_TYPE)
                        .build();

        PutObjectResponse response = s3Client.putObject(por, RequestBody.fromBytes(image_bytes));
    }

    /**
     * Returns the desidered image from the given specification.
     */
    public ResponseInputStream<GetObjectResponse> downloadJPEGImage(Integer image_code, ImageSpec spec){
        var JPEG_CONTENT_TYPE = "image/jpeg";
        GetObjectRequest gor = GetObjectRequest.builder().bucket(bucketName).key(image_code.toString()).responseContentType(JPEG_CONTENT_TYPE).build();
        ResponseInputStream<GetObjectResponse> response = s3Client.getObject(gor, ResponseTransformer.toInputStream());
        return response;
    }
}
