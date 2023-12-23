package com.web.auction.data;

import com.web.auction.models.Lot;
import com.web.auction.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LotRepository
        extends CrudRepository<Lot, Long> {

    List<Lot> findLotByUser(
            User user);

    List<Lot> findAllByOrderByPlacedAtDesc();

}
