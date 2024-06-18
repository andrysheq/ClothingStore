package com.web.auction.models;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @ElementCollection
    @CollectionTable(name = "cart_items", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Long, Integer> cart = new HashMap<>();

    public Cart(User user){
        this.user = user;
    }

    public Cart(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<Long, Integer> getCart() {
        return cart;
    }

    public void setCart(Map<Long, Integer> cart) {
        this.cart = cart;
    }

    public void addToCart(Long productId, int quantity) {
        cart.put(productId, quantity);
    }

    public void deleteFromCart(Long productId) {
        cart.remove(productId);
    }

    public void updateItemQuantity(Long productId, int newQuantity) {
        cart.put(productId, newQuantity);
    }

    public void increaseItemQuantity(Long productId){
        cart.put(productId, cart.getOrDefault(productId, 0) + 1);
    }

    public void degreaseItemQuantity(Long productId){
        cart.put(productId, cart.getOrDefault(productId, 0) - 1);
    }
}