package com.web.auction.data;

import com.web.auction.models.Lot;
import com.web.auction.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LotRepository
        extends CrudRepository<Lot, Long> {

    List<Lot> findLotByUser(
            User user);

    List<Lot> findAllByOrderByPlacedAtDesc();

}
