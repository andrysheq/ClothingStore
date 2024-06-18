package com.web.auction.data;

import com.web.auction.models.User;
import com.web.auction.models.Visit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends CrudRepository<Visit, Long> {

}
