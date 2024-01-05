package com.web.auction.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
public class News implements Serializable {
    public News(String nameOfNews,String text){
        this.nameOfNews = nameOfNews;
        this.text = text;
    }
    public News(){

    }
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Date placedAt;
    @ManyToOne
    private User user;
    private String nameOfNews;
    private String text;

    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }

    public String formatPlacedAt() {
        if (placedAt == null) {
            return "";  // или другое значение по умолчанию
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm, dd/MM/yyyy");
        return dateFormat.format(placedAt);
    }
}