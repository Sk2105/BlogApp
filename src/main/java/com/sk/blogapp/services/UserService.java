package com.sk.blogapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sk.blogapp.dto.UserDTO;
import com.sk.blogapp.jwt.JwtUtils;
import com.sk.blogapp.models.Image;
import com.sk.blogapp.models.User;
import com.sk.blogapp.repository.UserRepository;
import com.sk.blogapp.response.AuthorResponse;
import com.sk.blogapp.response.UserResponse;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ImageService imageService;

    @Value("${app.host}")
    private String host;

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
            userRepository.save(user);
            AuthorResponse author = userRepository.findAuthorById(user.getId());
            return new UserResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    author.imageUrl());
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
            AuthorResponse author = userRepository.findAuthorById(user.getId());
            return new UserResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    author.imageUrl());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UserResponse getMe() throws RuntimeException {
        try {
            UserDetails myUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByEmail(myUser.getUsername());
            AuthorResponse author = userRepository.findAuthorById(user.getId());
            return new UserResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    author.imageUrl());
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

            Image image = new Image();
            image.setContentType(file.getContentType());
            image.setFileName(file.getOriginalFilename());
            image.setData(file.getBytes());
            imageService.saveFile(image);

            User user = userRepository.findByEmail(getMe().email());
            user.setImageUrl(host + "/images/" + image.getId());
            userRepository.save(user);
            return "Image uploaded successfully";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
