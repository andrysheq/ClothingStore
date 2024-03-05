package com.web.auction.data;

import com.web.auction.models.User;
import com.web.auction.models.WishList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends CrudRepository<WishList, Long> {
    WishList findByUser(User user);
}