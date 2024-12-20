package com.sk.blogapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sk.blogapp.dto.UserDTO;
import com.sk.blogapp.response.MessageResponse;
import com.sk.blogapp.response.TokenResponse;
import com.sk.blogapp.response.UserResponse;
import com.sk.blogapp.services.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) throws RuntimeException {
        return ResponseEntity.ok(new TokenResponse(userService.login(userDTO)));
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserDTO userDTO) throws RuntimeException {
        return userService.registerUser(userDTO);
    }

    @GetMapping("/user")
    public UserResponse getMe() throws RuntimeException {
        return userService.getMe();
    }

    @PutMapping(value = "/user/profile", consumes = "multipart/form-data")
    public ResponseEntity<?> updateUser(@RequestParam(name = "file", required = true) MultipartFile file)
            throws RuntimeException {
        
        return ResponseEntity.ok(new MessageResponse(userService.uploadImage(file), HttpStatus.OK.value()));
    }

}
