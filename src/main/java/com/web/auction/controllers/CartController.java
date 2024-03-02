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
        Map<CartItem, String> cartListWithPhoto = new HashMap<>();
        List<CartItem> cart = cartRepository.findAllByUser(currentUser);
        for (CartItem product : cart) {
            byte[] photoBytes = product.getProduct().getPhotoOfProduct();
            String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
            cartListWithPhoto.put(product, photoBase64);
        }
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("cartItems", cartListWithPhoto);
        return "cart";
    }

    @PostMapping("/delete/{cartItemId}")
    public String deleteFromCart(@AuthenticationPrincipal User user,@PathVariable Long cartItemId){
        User currentUser = userRepo.findByUsername(user.getUsername());
        List<CartItem> cart = cartRepository.findAllByUser(currentUser);
        Iterator<CartItem> iterator = cart.iterator();
        while (iterator.hasNext()) {
            CartItem cartItem = iterator.next();
            if (cartItem.getProduct().getId().equals(cartItemId)) {
                iterator.remove();
                cartRepository.delete(cartItem); // Опционально, если нужно также удалить элемент из базы данных
            }
        }
        return "redirect:/cart";
    }

    @PostMapping("/increase/{cartItemId}")
    public String degreaseCartItemQuantity(@AuthenticationPrincipal User user,@PathVariable Long cartItemId){
        User currentUser = userRepo.findByUsername(user.getUsername());
        List<CartItem> cart = cartRepository.findAllByUser(currentUser);
        cart.forEach(o->{
            if(o.getProduct().getId().equals(cartItemId)){
                o.setQuantity(o.getQuantity()+1);
                cartRepository.save(o);
            }
        });
        return "redirect:/cart";
    }

    @PostMapping("/degrease/{cartItemId}")
    public String increaseCartItemQuantity(@AuthenticationPrincipal User user,@PathVariable Long cartItemId){
        User currentUser = userRepo.findByUsername(user.getUsername());
        List<CartItem> cart = cartRepository.findAllByUser(currentUser);
        cart.forEach(o->{
            if(o.getProduct().getId().equals(cartItemId)){
                o.setQuantity(o.getQuantity()-1);
                cartRepository.save(o);
            }
        });
        return "redirect:/cart";
    }
}