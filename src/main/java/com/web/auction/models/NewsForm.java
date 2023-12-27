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
public class NewsForm {
    @NotBlank(message="Поле 'Название статьи' не может быть пустым")
    private String nameOfNews;
    @NotBlank(message="Поле 'Текст статьи' не может быть пустым")
    private String text;

    public static NewsForm getNewsFormWithNews(News news){
        NewsForm form = new NewsForm();
        form.setNameOfNews(news.getNameOfNews());
        form.setText(news.getText());
        return form;
    }
    public News toNews() {
        return new News(nameOfNews, text);
    }
//    public static MultipartFile convert(byte[] bytes, String filename) throws IOException {
//        return new MockMultipartFile(filename, filename, "application/octet-stream", bytes);
//    }

    public String getNameOfNews() {
        return nameOfNews;
    }

    public void setNameOfNews(String nameOfNews) {
        this.nameOfNews = nameOfNews;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

//    public MultipartFile getPhoto() {
//        return photo;
//    }

//    public void setPhoto(MultipartFile photo) {
//        this.photo = photo;
//    }
}