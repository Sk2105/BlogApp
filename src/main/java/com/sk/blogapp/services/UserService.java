package com.sk.blogapp.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sk.blogapp.dto.UserDTO;
import com.sk.blogapp.jwt.JwtUtils;
import com.sk.blogapp.models.User;
import com.sk.blogapp.repository.UserRepository;
import com.sk.blogapp.response.UserResponse;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByEmail(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return user;
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public UserResponse registerUser(UserDTO userDTO) throws RuntimeException {
        try {
            User user = this.toUser(userDTO);
            return UserResponse.toUserResponse(userRepository.save(user));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User toUser(UserDTO userDTO) throws RuntimeException {
        User user = new User();
        user.setEmail(userDTO.email());
        user.setName(userDTO.name());
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        return user;

    }

    public String login(UserDTO userDTO) throws RuntimeException {
        try {
            UserDetails user = loadUserByUsername(userDTO.email());
            if (user == null) {
                throw new RuntimeException("User not found");
            } else if (!passwordEncoder.matches(userDTO.password(), user.getPassword())) {
                throw new RuntimeException("Invalid password");
            }
            String token = jwtUtils.generateToken(user.getUsername());
            return token;
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    public User getUser(String email) throws RuntimeException {
        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UserResponse getUserResponse(String email) throws RuntimeException {
        try {
            User user = getUser(email);
            return UserResponse.toUserResponse(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UserResponse getMe() throws RuntimeException {
        try {
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return UserResponse.toUserResponse(userRepository.findByEmail(user.getUsername()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Uploads an image file, validates it, and associates it with the current user.
     *
     * @param file the image file to upload, must be of type png, jpg, or jpeg
     * @return a message indicating the success of the upload
     * @throws RuntimeException if the file is empty, too large, or of an invalid
     *                          type
     */
    public String uploadImage(MultipartFile file) {
        try {
            @SuppressWarnings("null")
            String extension = file.getOriginalFilename().split("\\.")[1];
            if (!extension.equals("png") && !extension.equals("jpg") && !extension.equals("jpeg")) {
                throw new RuntimeException("Invalid file type");
            }

            if (file.isEmpty()) {
                throw new RuntimeException("File is empty");
            }

            if (file.getSize() > 1000000) {
                throw new RuntimeException("File size is too large");
            }

            String fileName = UUID.randomUUID().toString();
            File destFile = new File("src/main/resources/static/images/" + fileName + "." + extension);

            Path path = Paths.get(destFile.getAbsolutePath());
            Files.copy(file.getInputStream(), path);
            User user = userRepository.findByEmail(getMe().email());
            user.setImageUrl("http://localhost:8080/images/" + fileName);
            userRepository.save(user);
            return "Image uploaded successfully";
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
