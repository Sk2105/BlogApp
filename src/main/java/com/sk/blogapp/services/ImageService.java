package com.sk.blogapp.services;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.blogapp.models.Image;
import com.sk.blogapp.repository.ImageRepository;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public byte[] getFile(String fileName) throws FileNotFoundException {
        return imageRepository.findById(fileName).orElseThrow(() -> new FileNotFoundException("File not found"))
                .getData();

    }

    public void saveFile(Image image) throws Exception {
        imageRepository.save(image);
    }

}
