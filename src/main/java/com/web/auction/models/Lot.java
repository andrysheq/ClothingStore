package com.web.auction.models;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Lot implements Serializable {
    public Lot(String nameOfLot,String price){
        this.nameOfLot = nameOfLot;
        this.price = price;
    }
    public Lot(){

    }
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Date placedAt;
    @ManyToOne
    private User user;
    private String nameOfLot;
    private String price;
    private byte[] photoOfLot;
    private String city;
    private String street;

    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }
}
