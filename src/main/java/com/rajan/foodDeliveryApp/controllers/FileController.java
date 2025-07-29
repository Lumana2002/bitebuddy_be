package com.rajan.foodDeliveryApp.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final ResourceLoader resourceLoader;
    @Value("${file.upload-dir:src/main/resources/static/storage}")
    private String uploadDir;

    @Value("${file.base-url:http://localhost:8080}")
    private String baseUrl;

    public FileController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty.");
            }

            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filename = file.getOriginalFilename();
            if (filename == null) {
                return ResponseEntity.badRequest().body("Filename is not provided.");
            }
            filename = filename.replace("_", "");
            filename = getUniqueFileName(dir, filename);
            Path filePath = Paths.get(uploadDir, filename);

            Files.copy(file.getInputStream(), filePath);

            String image = String.format("%s/api/files/images/%s", baseUrl, filename);

            return ResponseEntity.ok().body(new FileUploadResponse(image));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }

    private String getUniqueFileName(File directory, String filename) {
        File file = new File(directory, filename);
        int count = 1;
        String baseName = getBaseName(filename);
        String extension = getFileExtension(filename);

        while (file.exists()) {
            String newFilename = String.format("%s(%d).%s", baseName, count++, extension);
            file = new File(directory, newFilename);
        }

        return file.getName();
    }

    private String getBaseName(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        return (lastDotIndex == -1) ? filename : filename.substring(0, lastDotIndex);
    }

    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        return (lastDotIndex == -1) ? "" : filename.substring(lastDotIndex + 1);
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Resource resource = resourceLoader.getResource("classpath:/static/storage/" + filename);

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    static class FileUploadResponse {
        private String image;

        public FileUploadResponse(String image) {
            this.image = image;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}


