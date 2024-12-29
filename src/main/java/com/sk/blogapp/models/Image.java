package com.sk.blogapp.models;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Image {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String id;

    private String fileName;

    private String contentType;

    @Lob
    private byte[] data;

}
