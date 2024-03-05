package com.web.auction.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
