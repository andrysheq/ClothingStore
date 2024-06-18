package com.web.auction.controllers;

import com.web.auction.data.CartRepository;
import com.web.auction.data.ProductRepository;
import com.web.auction.data.UserRepository;
import com.web.auction.data.WishListRepository;
import com.web.auction.models.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private final WishListRepository wishListRepository;
    private final CartRepository cartRepo;

    public ProductController(ProductRepository productRepo, UserRepository userRepo, CartRepository cartRepo, WishListRepository wishListRepository) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.cartRepo = cartRepo;
        this.wishListRepository = wishListRepository;
    }

    @GetMapping()
    public String productsForUser(
            @AuthenticationPrincipal User user, Model model) {


        List<Product> products = productRepo.findAll();
        Map<Product, String> productPhotoMap = new HashMap<>();

        for (Product product : products) {
            byte[] photoBytes = product.getPhotoOfProduct();
            String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
            productPhotoMap.put(product, photoBase64);
        }

        model.addAttribute("products", products);
        model.addAttribute("productPhotoMap", productPhotoMap);

        return "productsPanel";
    }

    @GetMapping("/{id}")
    public String openNews(@PathVariable Long id,
                           Model model) {
        Product product = productRepo.findProductById(id);
        byte[] photoBytes = product.getPhotoOfProduct();
        String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
        model.addAttribute("photo",photoBase64);
        model.addAttribute("product",product);
        return "productPage";
    }

    @PostMapping("/add-to-wishlist/{productId}")
    public String addToWishList(@PathVariable Long productId, @AuthenticationPrincipal User user, Model model) {
        Product product = productRepo.findProductById(productId);
        User currentUser = userRepo.findByUsername(user.getUsername());

        WishList wishList = wishListRepository.findByUser(currentUser);

        if (wishList == null) {
            wishList = new WishList();
            wishList.setUser(currentUser);
        }

        boolean alreadyExists = wishList.getProducts().stream()
                .anyMatch(p -> p.getId().equals(productId));

        if(!alreadyExists) {
            wishList.add(product);
            wishListRepository.save(wishList);
        }

        return "redirect:/";
    }

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId, @AuthenticationPrincipal User user, Model model,
                            @ModelAttribute("quantity") String quantity) {
        Product product = productRepo.findProductById(productId);
        User currentUser = userRepo.findByUsername(user.getUsername());

        CartItem cartItem = new CartItem();
        cartItem.setUser(currentUser);
        cartItem.setProduct(product);
        cartItem.setQuantity(Integer.parseInt(quantity)); // Или любое другое начальное количество

        cartRepo.save(cartItem);

        return "redirect:/";
    }
}
