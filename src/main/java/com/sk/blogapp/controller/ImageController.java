package com.sk.blogapp.controller;

import java.io.File;
import java.io.FileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sk.blogapp.services.ImageService;

@RequestMapping("/api/images")
@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/{fileName}")
    public ResponseEntity<?> getImageFile(
            @PathVariable String fileName) throws FileNotFoundException {
        File file = imageService.getFile(fileName);
        String fileNameWithExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        return ResponseEntity.ok()
                .contentType(new MediaType("image", fileNameWithExtension))
                .body(new FileSystemResource(imageService.getFile(fileName)));
    }

}
