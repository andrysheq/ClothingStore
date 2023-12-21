package com.web.auction.models;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Data
public class LotForm {
    private String nameOfLot;
    private String price;
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
