package com.sk.blogapp.services;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.stereotype.Service;


@Service
public class ImageService {

    public File getFile(String fileName) throws FileNotFoundException {
        File pngFile= new File("src/main/resources/static/images/" + fileName + ".png");
        File jpgFile = new File("src/main/resources/static/images/" + fileName + ".jpg");
        if (!pngFile.exists() && !jpgFile.exists()) {
            throw new FileNotFoundException("File not found");
        }
        return (pngFile.exists()) ? pngFile : jpgFile;
    }
    
}
