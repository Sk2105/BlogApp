package com.sk.blogapp.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "blogs")
public class Blog {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String id;

    @Column(nullable = false, unique = true)
    private String title;



    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User author;

    @ManyToMany
    private List<User> favoritedBy = new ArrayList<>();

    @Column(nullable = false)
    private Date createdAt = new Date(System.currentTimeMillis());

    @Column
    private Date modifiedAt = new Date(System.currentTimeMillis());

}
