package com.web.auction.controllers;

import com.web.auction.data.CartRepository;
import com.web.auction.data.ProductRepository;
import com.web.auction.data.UserRepository;
import com.web.auction.models.CartItem;
import com.web.auction.models.Product;
import com.web.auction.models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final CartRepository cartRepository;

    public CartController(UserRepository userRepo, ProductRepository productRepo, CartRepository cartRepository) {
        this.userRepo = userRepo;
        this.productRepo = productRepo;
        this.cartRepository = cartRepository;
    }

    @GetMapping
    public String viewCart(Model model, @AuthenticationPrincipal User user) {
        User currentUser = userRepo.findByUsername(user.getUsername());
        Map<CartItem, String> cartListWithPhoto = new HashMap<>();
        List<CartItem> cart = cartRepository.findAllByUser(currentUser);
        for (CartItem product : cart) {
            byte[] photoBytes = product.getProduct().getPhotoOfProduct();
            String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
            cartListWithPhoto.put(product, photoBase64);
        }
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("cartItems", cartRepository.findAllByUser(currentUser));
        return "cart";
    }

//    @PostMapping("/add/{productId}")
//    public String addToCart(@PathVariable Long productId, @AuthenticationPrincipal User user) {
//        Product product = productRepo.findProductById(productId);
//        User currentUser = userRepo.findByUsername(user.getUsername());
//
//        CartItem cartItem = new CartItem();
//        cartItem.setUser(currentUser);
//        cartItem.setProduct(product);
//        cartItem.setQuantity(1); // Или любое другое начальное количество
//
//        cartRepository.save(cartItem);
//
//        return "redirect:/cart";
//    }
}