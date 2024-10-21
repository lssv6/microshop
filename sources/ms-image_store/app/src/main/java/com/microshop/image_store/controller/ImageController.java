package com.microshop.image_store.controller;

import com.microshop.image_store.services.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;

import com.microshop.image_store.dto.SaveImageRequest;
import com.microshop.image_store.images.ImageSpec;

@Controller
@RequestMapping("/image-store")
public class ImageController {

    @Autowired private ImageService imageService;

    @PostMapping(path = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> uploadImage(
        @ModelAttribute SaveImageRequest saveImageRequest
    ) {
        Integer imageCode = saveImageRequest.getCode();
        MultipartFile imageFile = saveImageRequest.getImageFile();
        if(imageFile != null){
            try{
                imageService.uploadJPEGImage(imageCode, imageFile);
            }catch(IOException e){
                return ResponseEntity.status(500).build();
            }
            return ResponseEntity.ok(null);
        }

        // FATAL failure ImageFIle cannot be empty!
        System.out.println("AAAAA, ImageFile is null");
        return ResponseEntity.status(500).build();
    }


    @GetMapping("/{image_code}")
    public ResponseEntity<Resource> getImage(
        @RequestBody ImageSpec spec,
        @PathVariable(name = "image_code") int image_code
    ){
        ResponseInputStream<GetObjectResponse> ris = imageService.downloadJPEGImage(image_code, spec);
        InputStreamResource resource = new InputStreamResource(ris);
        try{
            var response = ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).contentLength(resource.contentLength()).body((Resource)resource);
            return response;
        }catch(IOException ex){
            return ResponseEntity.status(500).build();
        }

    }
}

