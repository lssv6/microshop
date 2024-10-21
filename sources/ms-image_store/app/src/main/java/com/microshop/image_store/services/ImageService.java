package com.microshop.image_store.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import com.microshop.image_store.images.ImageSpec;

import java.io.IOException;
import java.io.InputStream;
@Service
public class ImageService {
    private static final String bucketName = "microshop.product-images";
    private final S3Client s3Client;

    private static final String JPEG_CONTENT_TYPE = "image/jpeg";

    @Autowired
    public ImageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }
    
    public void uploadJPEGImage(Integer imageCode, MultipartFile imageFile) throws IOException{
        PutObjectRequest por =
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(imageCode.toString())
                        .contentType(JPEG_CONTENT_TYPE)
                        .build();
        InputStream imageFileInputStream = imageFile.getInputStream();
        RequestBody rb = RequestBody.fromInputStream(imageFileInputStream, imageFile.getSize());
        s3Client.putObject(por, rb);
    }

    /**
     * Uploads an JPEG image to the S3.
     * It's an idepotent operation, if such file with image_code already
     * exists, then it'll be overriden !!
     *
     * @param image_code - is the code of the image
     * @param image_bytes - is the binary of the image (must be jpeg)
     */
    public void uploadJPEGImage(Integer imageCode, InputStream imageInputStream, Long length) {

        PutObjectRequest por =
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(imageCode.toString())
                        .contentType(JPEG_CONTENT_TYPE)
                        .build();
        RequestBody rb = RequestBody.fromInputStream(imageInputStream, length);
        s3Client.putObject(por, rb);
    }

    /**
     * Returns the desidered image from the given specification.
     */
    public ResponseInputStream<GetObjectResponse> downloadJPEGImage(Integer image_code, ImageSpec spec){
        GetObjectRequest gor = GetObjectRequest.builder().bucket(bucketName).key(image_code.toString()).responseContentType(JPEG_CONTENT_TYPE).build();
        ResponseInputStream<GetObjectResponse> response = s3Client.getObject(gor, ResponseTransformer.toInputStream());
        return response;
    }

    /**
     *
     */
}
