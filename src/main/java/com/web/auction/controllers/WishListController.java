package com.web.auction.controllers;

import com.web.auction.data.CartRepository;
import com.web.auction.data.ProductRepository;
import com.web.auction.data.UserRepository;
import com.web.auction.data.WishListRepository;
import com.web.auction.models.Product;
import com.web.auction.models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wishlist")
public class WishListController {
    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final WishListRepository wishListRepository;

    public WishListController(UserRepository userRepo, ProductRepository productRepo, WishListRepository wishListRepository) {
        this.userRepo = userRepo;
        this.productRepo = productRepo;
        this.wishListRepository = wishListRepository;
    }

    @GetMapping
    public String viewCart(Model model, @AuthenticationPrincipal User user) {
        User currentUser = userRepo.findByUsername(user.getUsername());
        Map<Product, String> wishListWithPhoto = new HashMap<>();
        List<Product> wishList = wishListRepository.findByUser(currentUser).getProducts();
        for (Product product : wishList) {
            byte[] photoBytes = product.getPhotoOfProduct();
            String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
            wishListWithPhoto.put(product, photoBase64);
        }
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("wishListItemsMap",wishListWithPhoto);
        return "wishlist";
    }
}
