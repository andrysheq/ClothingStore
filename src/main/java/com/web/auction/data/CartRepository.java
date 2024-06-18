package com.web.auction.data;

import com.web.auction.models.Cart;
import com.web.auction.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
    Cart findByUser(User user);
}
