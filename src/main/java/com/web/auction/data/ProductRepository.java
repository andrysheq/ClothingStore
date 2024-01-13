package com.web.auction.data;

import com.web.auction.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository
        extends CrudRepository<Product, Long> {

    List<Product> findAll();

    Product findProductById(Long id);


}
