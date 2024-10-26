package com.microshop.image_store.controller;

import com.microshop.image_store.dto.SaveImageRequest;
import com.microshop.image_store.images.ImageSpec;
import com.microshop.image_store.services.ImageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/image-store")
public class ImageController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired private ImageService imageService;

    /**
     * Endpoint responsible for saving the image on cloud. All the images saved here are written as
     * follows:
     *
     * <p>image_code/size/quality.
     *
     * <p>For each size: EXTRA_SMALL, SMALL, MEDIUM, LARGE, EXTRA_LARGE. All written with the
     * quality of 100%.
     *
     * @param saveImageRequest it's the form model that is required to save the image.
     */
    @PostMapping(
            path = "",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> uploadImage(@ModelAttribute SaveImageRequest saveImageRequest) {
        Integer imageCode = saveImageRequest.getCode();
        MultipartFile imageFile = saveImageRequest.getImageFile();

        if (imageFile != null) {
            logger.info("Trying to save the images to the cloud");
            try {
                for (ImageSpec.Size size : ImageSpec.Size.values()) {
                    imageService.uploadJPEGImage(imageCode, imageFile, new ImageSpec(size, 100));
                    logger.info("Saved a image to the cloud");
                }
            } catch (IOException e) {
                return ResponseEntity.status(500).build();
            }
            return ResponseEntity.ok(null);
        }

        // FATAL failure ImageFIle cannot be empty!
        logger.error("The given image file is null");
        return ResponseEntity.status(500).build();
    }

    @GetMapping("/{image_code}")
    public ResponseEntity<Resource> downloadImage(
            @RequestBody ImageSpec spec, @PathVariable(name = "image_code") int image_code) {
        InputStream ris = imageService.downloadJPEGImage(image_code, spec);
        Resource resource = new InputStreamResource(ris);
        var response =
                ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        // ### HACK. You're unable to know the length of a input stream easily.
                        // So, we'll make the client blind to the real size of the image.
                        // it's very boring that the client cannot download on a parallel way.
                        // BORING !!!!
                        // .contentLength(resource.contentLength())
                        .body(resource);
        return response;
    }
}
