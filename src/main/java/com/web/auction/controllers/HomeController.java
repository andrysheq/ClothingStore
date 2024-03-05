package com.web.auction.controllers;

import com.web.auction.data.ProductRepository;
import com.web.auction.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private ProductRepository productRepo;
    @GetMapping
    public String home(Model model) {
        List<Product> products = productRepo.findAll();

        model.addAttribute("products", products);
        return "home";
    }

    @Autowired
    public HomeController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }
}
