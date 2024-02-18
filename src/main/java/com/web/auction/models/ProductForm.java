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
public class ProductForm {
    @NotBlank(message="Поле 'Название' не может быть пустым")
    private String name;
    @Pattern(regexp = "^[1-9]\\d{0,8}$", message = "Некорректный формат цены")
    private String price;
    @NotBlank(message="Поле 'Пол' не может быть пустым")
    private String gender;
    @NotBlank(message="Поле 'Категория' не может быть пустым")
    private String category;
    @NotNull(message="Загрузите фото вашего лота")
    private MultipartFile photo;

    public static ProductForm getLotFormWithLot(Product product){
        ProductForm form = new ProductForm();
        form.setName(product.getName());
        form.setPrice(product.getPrice());
        byte[] yourByteArray = product.getPhotoOfProduct();
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
    public Product toProduct() {
        Product product = new Product(name,price,category,gender);
        try {
            product.setPhotoOfProduct(photo.getBytes());
        } catch (IOException e) {

        }
        return product;
    }

    public static ProductForm getProductFormWithProduct(Product product){
        ProductForm form = new ProductForm();
        form.setName(product.getName());
        form.setPrice(product.getPrice());
        form.setCategory(product.getCategory());
        form.setGender(product.getGender());
        return form;
    }
    public static MultipartFile convert(byte[] bytes, String filename) throws IOException {
        return new MockMultipartFile(filename, filename, "application/octet-stream", bytes);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
