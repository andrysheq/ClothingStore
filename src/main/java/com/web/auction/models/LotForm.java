package com.web.auction.models;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.IOException;
@Component
@Data
public class LotForm {
    @NotBlank(message="Поле 'Название' не может быть пустым")
    private String nameOfLot;
    @Pattern(regexp = "^[1-9]\\d{0,8}$", message = "Некорректный формат цены")
    private String price;
    @NotNull(message="Загрузите фото вашего лота")
    private MultipartFile photo;
    public Lot toLot() {
        Lot lot = new Lot(nameOfLot, price);
        try {
            lot.setPhotoOfLot(photo.getBytes());
        } catch (IOException e) {

        }
        return lot;
    }
}
