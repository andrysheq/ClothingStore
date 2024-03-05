package com.web.auction.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Base64;

@Data
@Entity
public class Product implements Serializable {
    public Product(String name,String price,String category,String gender){
        this.name = name;
        this.price = price;
        this.category = category;
        this.gender = gender;
    }
    public Product(){

    }
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String price;
    private String gender;
    private String category;
    private byte[] photoOfProduct;
    public String getPhotoToHtml() {
        return Base64.getEncoder().encodeToString(photoOfProduct);
    }
}
