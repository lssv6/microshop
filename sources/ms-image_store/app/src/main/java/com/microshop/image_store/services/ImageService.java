package com.microshop.image_store.services;

import com.microshop.image_store.images.ImageSpec;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

@Service
public class ImageService {

    // private static final Logger log = LoggerFactory.getLogger(ImageService.class);
    private static final String bucketName = "microshop.product-images";
    private final S3Client s3Client;

    @Autowired
    public ImageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    private ImageWriter createJpegImageWriter() {
        Iterator<ImageWriter> writers =
                ImageIO.getImageWritersByMIMEType(MediaType.IMAGE_JPEG_VALUE);
        ImageWriter jpegImageWriter = writers.next();
        return jpegImageWriter;
    }

    private byte[] compressImage(InputStream imageInputStream, ImageSpec spec) throws IOException {
        // Reads the image from an arbritrary InputStream. Throws IOException if the image cannot be
        // read.
        BufferedImage buffImage = ImageIO.read(imageInputStream); // Reads from any given format.

        // Resizes the image.
        BufferedImage buffResizedImage = Scalr.resize(buffImage, spec.getSize().getWidth());

        // Create ByteArrayOutputStream in order to store the resulting image binary.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(baos);

        // Creates an JPEG image writer object that is responsible for all the transformations.
        ImageWriter jpegImageWriter = createJpegImageWriter();
        jpegImageWriter.setOutput(imageOutputStream);

        // Specifies all the needed parameters for image compression!
        ImageWriteParam params = jpegImageWriter.getDefaultWriteParam();
        params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        params.setCompressionQuality(spec.getQuality() / 100f);

        // Writes the image specified by params to imageOutputStream.
        jpegImageWriter.write(null, new IIOImage(buffResizedImage, null, null), params);
        jpegImageWriter.dispose();
        return baos.toByteArray();
    }

    public void uploadJPEGImage(Integer imageCode, MultipartFile imageFile, ImageSpec imageSpec)
            throws IOException {
        uploadJPEGImage(imageCode, imageFile.getInputStream(), imageFile.getSize(), imageSpec);
    }

    /**
     * Uploads an JPEG image to the S3. It's an idepotent operation, if such file with image_code
     * already exists, then it'll be overriden !!
     *
     * @param imageCode - is the code of the image
     * @param imageInputStream - is the binary of the image (must be jpeg)
     */
    public void uploadJPEGImage(
            Integer imageCode, InputStream imageInputStream, Long length, ImageSpec imageSpec)
            throws IOException {
        byte[] imageBytes = compressImage(imageInputStream, imageSpec);
        PutObjectRequest por =
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key("%s/%s".formatted(imageCode, imageSpec.getSize().getAbbreviation()))
                        .contentType(MediaType.IMAGE_JPEG_VALUE)
                        .build();
        RequestBody rb = RequestBody.fromBytes(imageBytes);
        s3Client.putObject(por, rb);
    }

    /** Returns the desidered image from the given specification. */
    public InputStream downloadJPEGImage(Integer imageCode, ImageSpec imageSpec) {
        GetObjectRequest gor =
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key("%s/%s".formatted(imageCode, imageSpec.getSize().getAbbreviation()))
                        .responseContentType(MediaType.IMAGE_JPEG_VALUE)
                        .build();
        return s3Client.getObject(gor, ResponseTransformer.toInputStream());
    }
}
