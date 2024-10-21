package com.microshop.image_store.controller;

import com.microshop.image_store.services.ImageUploader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;

import com.microshop.image_store.dto.SaveImageRequest;
import com.microshop.image_store.images.ImageSpec;

@Controller
@RequestMapping("/image-store")
public class ImageController {

    @Autowired private ImageUploader imageUploader;

    @PostMapping(path = "form",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> uploadImage(
        @ModelAttribute SaveImageRequest saveImageRequest
    ) {
        try {
            var image_code = saveImageRequest.getCode();
            var image_file = saveImageRequest.getImageFile();
            if(image_file == null){
                System.out.println("AAAAA, ImageFile is null");
                return ResponseEntity.status(500).build();
            }
            imageUploader.uploadJPEGImage(image_code, image_file.getBytes());
            return ResponseEntity.ok(null);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{image_code}")
    public ResponseEntity<Resource> getImage(
        @RequestBody ImageSpec spec,
        @PathVariable(name = "image_code") int image_code
    ){
        ResponseInputStream<GetObjectResponse> ris = imageUploader.downloadJPEGImage(image_code, spec);
        InputStreamResource resource = new InputStreamResource(ris);
        try{
            var response = ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).contentLength(resource.contentLength()).body((Resource)resource);
            return response;
        }catch(IOException ex){
            return ResponseEntity.status(500).build();
        }

    }
}

