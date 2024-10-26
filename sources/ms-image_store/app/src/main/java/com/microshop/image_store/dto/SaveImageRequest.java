package com.microshop.image_store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class SaveImageRequest {
    @NonNull private final Integer code;

    /** Can be one of any of the following file formats: - BMP - GIF - JPEG - PNG - TIFF - WBMP */
    @NonNull private final MultipartFile imageFile;
}
