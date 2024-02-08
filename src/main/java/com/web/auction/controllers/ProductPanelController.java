package com.web.auction.controllers;

import com.web.auction.data.ProductRepository;
import com.web.auction.data.UserRepository;
import com.web.auction.models.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.web.auction.models.ProductForm.getProductFormWithProduct;

@Component
@Controller
@PreAuthorize("hasRole('ROLE_MODERATOR')")
@RequestMapping("/products-panel")
public class ProductPanelController {
    private UserRepository userRepo;
    private ProductRepository productRepo;
    private ProductForm form;

    public ProductPanelController(UserRepository userRepo, ProductRepository productRepo, ProductForm form) {
        this.userRepo = userRepo;
        this.productRepo = productRepo;
        this.form = form;
    }
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping("/current")
    public String orderForm(Model model,@AuthenticationPrincipal User user,
                            @ModelAttribute Product product) {

//        if (lot.getStreet() == null) {
//            lot.setStreet(user.getStreet());
//        }
//        if (lot.getCity() == null) {
//            lot.setCity(user.getCity());
//        }
        String gender = "";
        String category = "";
        model.addAttribute("gender", gender);
        model.addAttribute("category", category);
        model.addAttribute("productForm", form);
        return "createProduct";
    }
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping
    public String processProduct(@ModelAttribute("productForm") @Valid ProductForm form,
                                 @ModelAttribute("category") String category,
                                 @ModelAttribute("gender") String gender,
                              BindingResult result,
                              Model model, Errors errors,
                              SessionStatus sessionStatus,
                              @AuthenticationPrincipal User user) {

        if (errors.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            model.addAttribute("productForm", form);
            return "createProduct";
        }

        Product productToSave = form.toProduct();

        productToSave.setCategory(category);
        productToSave.setGender(gender);

        productRepo.save(productToSave);

        sessionStatus.setComplete();

        return "redirect:/products-panel";
    }
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping
    public String productsForModerator(
            @AuthenticationPrincipal User user, Model model) {

        List<Product> products = productRepo.findAll();

        Map<Product, String> productPhotoMap = new HashMap<>();

        for (Product product : products) {
            byte[] photoBytes = product.getPhotoOfProduct();
            String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
            productPhotoMap.put(product, photoBase64);
        }

        //model.addAttribute("products", products);
        model.addAttribute("productPhotoMap", productPhotoMap);
        return "productsPanel";
    }
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping("/{id}")
    public String updateNews(@PathVariable Long id,
                             @ModelAttribute("productForm") @Valid ProductForm form,
                             @ModelAttribute("category") String category,
                             @ModelAttribute("gender") String gender,
                             BindingResult result,
                             Model model, Errors errors,
                             SessionStatus sessionStatus,
                             @AuthenticationPrincipal User user) {

        if (errors.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            model.addAttribute("productForm", form);
            Product product = productRepo.findProductById(id);
            model.addAttribute("product", product);
            return "editProduct";
        }
        Product productOld = productRepo.findProductById(id);
        Product productToUpdate = form.toProduct();

        productToUpdate.setId(id);
        productToUpdate.setGender(gender);
        productToUpdate.setCategory(category);

        productRepo.save(productToUpdate);

        sessionStatus.setComplete();

        return "redirect:/products-panel";
    }
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping("/{id}/edit")
    public String editProducts(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
        Product product = productRepo.findProductById(id);
        String gender = product.getGender();
        String category = product.getCategory();
        model.addAttribute("gender", gender);
        model.addAttribute("category", category);
        model.addAttribute("product", product);
        model.addAttribute("productForm", getProductFormWithProduct(product));
        return "editProduct";
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping("/delete-product")
    public String deleteProduct(@RequestParam("productId") Long productId) {

        Product product = productRepo.findById(productId).orElse(null);
        if (product != null) {
            // Удаление пользователя из репозитория
            productRepo.delete(product);

            // Вернуть пользователя на страницу администратора после удаления
            return "redirect:/products-panel";
        }

        // Если пользователь не найден, вернуть на страницу с сообщением об ошибке или другую страницу
        return "redirect:/products-panel?error=UserNotFound";
    }
}
