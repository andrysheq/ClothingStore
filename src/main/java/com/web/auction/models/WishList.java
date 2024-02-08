package com.web.auction.models;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @ManyToMany
    private List<Product> products = new ArrayList<>();;

    public void add(Product product){
        products.add(product);
    }

    public void clear(){
        products.clear();
    }
}
