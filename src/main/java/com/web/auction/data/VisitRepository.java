package com.web.auction.data;

import com.web.auction.models.User;
import com.web.auction.models.Visit;
import org.springframework.data.repository.CrudRepository;

public interface VisitRepository extends CrudRepository<Visit, Long> {

}
