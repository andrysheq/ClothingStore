package com.web.auction.security;

import com.web.auction.data.RoleRepository;
import com.web.auction.data.UserRepository;
import com.web.auction.models.Lot;
import com.web.auction.models.LotForm;
import com.web.auction.models.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.IOException;

import static com.web.auction.models.LotForm.convert;

@Component
@Data
public class EditUserForm {
    private MultipartFile photo;

    private final UserRepository userRepository;
    @NotBlank(message="Поле 'Логин' не может быть пустым")
    private String username;
    @NotBlank(message="Поле 'Имя' не может быть пустым")
    private String fullName;
    @NotBlank(message="Поле 'Улица' не может быть пустым")
    private String street;
    @NotBlank(message="Поле 'Город' не может быть пустым")
    private String city;
    @NotBlank(message="Поле 'Область' не может быть пустым")
    private String state;
    @NotBlank(message="Поле 'Индекс' не может быть пустым")
    private String zip;
    @Pattern(regexp = "\\+\\d{11}", message = "Некорректный формат номера телефона")
    private String phone;
    @Autowired
    public EditUserForm(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User toUser(PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        User user = new User(
                username, fullName, street, city, state, zip, phone);
        try {
            user.setPhoto(photo.getBytes()); // Предполагается, что User имеет поле byte[] для хранения фото
        } catch (IOException e) {
            // Обработка ошибки, если необходимо
        }
        user.setAccountNonLocked(true);
        return user;
    }
}
