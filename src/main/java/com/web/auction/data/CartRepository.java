package com.web.auction.data;

import com.web.auction.models.CartItem;
import com.web.auction.models.News;
import com.web.auction.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartRepository extends CrudRepository<CartItem, Long> {
    List<CartItem> findAllByUser(User user);

    List<CartItem> findAll();

    CartItem findCartItemById(Long id);
}
