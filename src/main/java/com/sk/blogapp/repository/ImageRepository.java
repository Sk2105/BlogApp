package com.sk.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sk.blogapp.models.Image;

public interface ImageRepository  extends JpaRepository<Image, String> {
    
}
