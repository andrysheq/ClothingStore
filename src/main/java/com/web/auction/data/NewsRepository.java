package com.web.auction.data;

import com.web.auction.models.News;
import com.web.auction.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NewsRepository extends CrudRepository<News, Long> {

    List<News> findAllByOrderByPlacedAtDesc();

    List<News> findAll();

    News findNewsById(Long id);

}
