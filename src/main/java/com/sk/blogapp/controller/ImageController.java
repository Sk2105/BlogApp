package com.sk.blogapp.controller;

import java.io.FileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sk.blogapp.services.ImageService;

@RequestMapping("/images")
@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/{fileName}")
    public ResponseEntity<?> getImageFile(
            @PathVariable String fileName) throws FileNotFoundException {
        byte[] file = imageService.getFile(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);
    }

}
