package com.web.auction.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @ElementCollection
    private Set<Long> productIds = new HashSet<>();
    public WishList() {
    }

    public WishList(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    public WishList(Long id) {
        this.id = id;
    }

    public WishList(User user) {
        this.user = user;
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

    public Set<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<Long> productIds) {
        this.productIds = productIds;
    }

    public Set<Long> getWishList(){
        return productIds;
    }

    public void addProductId(Long productId) {
        productIds.add(productId);
    }

    public void removeProductId(Long productId) {
        productIds.remove(productId);
    }

    public boolean containsProductId(Long productId) {
        return productIds.contains(productId);
    }

    public void clear() {
        productIds.clear();
    }
}
