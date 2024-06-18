package com.web.auction.models;

import lombok.Data;
import org.springframework.mock.web.MockMultipartFile;
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

    public static LotForm getLotFormWithLot(Lot lot){
        LotForm form = new LotForm();
        form.setNameOfLot(lot.getNameOfLot());
        form.setPrice(lot.getPrice());
        byte[] yourByteArray = lot.getPhotoOfLot();
        String fileName = "example.txt";
        MultipartFile multipartFile = null;
        try {
            multipartFile = convert(yourByteArray, fileName);
            // Дальнейшие действия с multipartFile
        } catch (IOException e) {
            e.printStackTrace();
        }
        form.setPhoto(multipartFile);
        return form;
    }
    public Lot toLot() {
        Lot lot = new Lot(nameOfLot, price);
        try {
            lot.setPhotoOfLot(photo.getBytes());
        } catch (IOException e) {

        }
        return lot;
    }
    public static MultipartFile convert(byte[] bytes, String filename) throws IOException {
        return new MockMultipartFile(filename, filename, "application/octet-stream", bytes);
    }

    public String getNameOfLot() {
        return nameOfLot;
    }

    public void setNameOfLot(String nameOfLot) {
        this.nameOfLot = nameOfLot;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }
}
