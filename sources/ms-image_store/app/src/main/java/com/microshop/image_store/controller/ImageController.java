package com.microshop.image_store.controller;

import com.microshop.image_store.services.ImageUploader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired private ImageUploader imageUploader;

    @PostMapping("/{img_code}")
    public ResponseEntity<PutObjectResponse> uploadImage(
            @PathVariable int image_code, @RequestParam MultipartFile jpeg_image) {
        try {
            PutObjectResponse response =
                    imageUploader.uploadJPEGImage(image_code, jpeg_image.getBytes());
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
