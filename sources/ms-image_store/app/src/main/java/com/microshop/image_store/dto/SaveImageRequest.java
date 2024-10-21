package com.microshop.image_store.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import software.amazon.awssdk.annotations.NotNull;

@ToString
@Data
@AllArgsConstructor
public class SaveImageRequest{
    @NotNull
    private final Integer code;

    private MultipartFile imageFile;
}
