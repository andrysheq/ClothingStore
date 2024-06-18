package com.web.auction.data;

import com.web.auction.models.CartItem;
import com.web.auction.models.Product;
import com.web.auction.models.User;
import com.web.auction.models.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    WishList findByUser(User user);
}