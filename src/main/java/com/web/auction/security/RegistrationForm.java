package com.web.auction.security;

import com.web.auction.data.RoleRepository;
import com.web.auction.data.UserRepository;
import com.web.auction.models.Role;
import com.web.auction.models.User;
import lombok.Data;
import javax.validation.constraints.AssertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.IOException;

@Component
@Data
public class RegistrationForm {
    private MultipartFile photo;

    private final UserRepository userRepository;
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Некорректный формат пароля")
//    private String confirmPassword;
    @NotBlank(message="Поле 'Логин' не может быть пустым")
    private String username;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Некорректный формат пароля")
    private String password;
//    @NotBlank(message="Поле 'Имя' не может быть пустым")
//    private String fullName;
//    @NotBlank(message="Поле 'Улица' не может быть пустым")
//    private String street;
//    @NotBlank(message="Поле 'Город' не может быть пустым")
//    private String city;
//    @NotBlank(message="Поле 'Область' не может быть пустым")
//    private String state;
//    @NotBlank(message="Поле 'Индекс' не может быть пустым")
//    private String zip;
    @Pattern(regexp = "\\+\\d{11}", message = "Некорректный формат номера телефона")
    private String phone;
    @Autowired
    public RegistrationForm(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public User toUser(PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
//        User user = new User(
//                username, passwordEncoder.encode(password),
//                fullName, street, city, state, zip, phone);
//        try {
//            user.setPhoto(photo.getBytes()); // Предполагается, что User имеет поле byte[] для хранения фото
//        } catch (IOException e) {
//            // Обработка ошибки, если необходимо
//        }
//        user.addRole(roleRepository.findById("USR"));
//        user.setAccountNonLocked(true);
//        return user;
//    }

    public User toUser(PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        User user = new User(
                username, passwordEncoder.encode(password), phone);
        try {
            user.setPhoto(photo.getBytes()); // Предполагается, что User имеет поле byte[] для хранения фото
        } catch (IOException e) {
            // Обработка ошибки, если необходимо
        }
        user.addRole(roleRepository.findById("USR"));
        user.setAccountNonLocked(true);
        return user;
    }

//    @AssertTrue(message = "Пароли должны совпадать")
//    public boolean isPasswordMatching() {
//        return password != null && password.equals(confirmPassword);
//    }
}
