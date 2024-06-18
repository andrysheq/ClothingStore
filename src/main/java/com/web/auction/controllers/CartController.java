package com.web.auction.controllers;

import com.web.auction.data.CartRepository;
import com.web.auction.data.ProductRepository;
import com.web.auction.data.UserRepository;
import com.web.auction.models.Cart;
import com.web.auction.models.Product;
import com.web.auction.models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
        Map<Product, Integer> cartListWithQuantity = new HashMap<>();
        Cart cart = cartRepository.findByUser(currentUser);
        if(cart!=null) {
            Map<Long, Integer> cartMap = cart.getCart();
            cartMap.forEach((productId, quantity) -> {
                cartListWithQuantity.put(productRepo.findProductById(productId), quantity);
            });
        }else{
            cart = new Cart(currentUser);
            cartRepository.save(cart);
        }
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("cartItems", cartListWithQuantity);
        return "cart";
    }

    @PostMapping("/delete/{cartItemId}")
    public String deleteFromCart(@AuthenticationPrincipal User user,@PathVariable Long cartItemId){
        User currentUser = userRepo.findByUsername(user.getUsername());
        Cart userCart = cartRepository.findByUser(currentUser);
        userCart.deleteFromCart(cartItemId);
        cartRepository.save(userCart);
        return "redirect:/cart";
    }

    @PostMapping("/increase/{cartItemId}")
    public String degreaseCartItemQuantity(@AuthenticationPrincipal User user,@PathVariable Long cartItemId){
        User currentUser = userRepo.findByUsername(user.getUsername());
        Cart userCart = cartRepository.findByUser(currentUser);
        userCart.degreaseItemQuantity(cartItemId);
        cartRepository.save(userCart);
        return "redirect:/cart";
    }

    @PostMapping("/degrease/{cartItemId}")
    public String increaseCartItemQuantity(@AuthenticationPrincipal User user,@PathVariable Long cartItemId){
        User currentUser = userRepo.findByUsername(user.getUsername());
        Cart userCart = cartRepository.findByUser(currentUser);
        userCart.increaseItemQuantity(cartItemId);
        cartRepository.save(userCart);
        return "redirect:/cart";
    }
}