package com.web.auction.security;

import com.web.auction.models.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Data
public class RegistrationForm {
    private MultipartFile photo;
    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;
    public User toUser(PasswordEncoder passwordEncoder) {
        User user = new User(
                username, passwordEncoder.encode(password),
                fullname, street, city, state, zip, phone);
        try {
            user.setPhoto(photo.getBytes()); // Предполагается, что User имеет поле byte[] для хранения фото
        } catch (IOException e) {
            // Обработка ошибки, если необходимо
        }
        return user;
    }
}
