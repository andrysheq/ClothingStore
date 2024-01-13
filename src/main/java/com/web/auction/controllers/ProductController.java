package com.web.auction.controllers;

import com.web.auction.data.ProductRepository;
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
    //private final LotForm form;

    public ProductController(ProductRepository productRepo) {
        this.productRepo = productRepo;
        //this.form = form;
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

        return "productListForModer";
    }

//    @PostMapping("/{id}")
//    public String updateLot(@PathVariable Long id,
//                            @ModelAttribute("lotForm") @Valid LotForm form,
//                            BindingResult result,
//                            @RequestParam("photo") MultipartFile photo,
//                            Model model, Errors errors,
//                            SessionStatus sessionStatus,
//                            @AuthenticationPrincipal User user) {
//
//        if (errors.hasErrors()) {
//            model.addAttribute("errors", result.getAllErrors());
//            model.addAttribute("lotForm", form);
//            Lot lot = lotRepo.findLotById(id);
//            model.addAttribute("lot", lot);
//            return "editLot";
//        }
//
////        try {
////            if(photo.getSize() <= 0) {
////                result.rejectValue("photo", "photoNotAdded", "Вы не загрузили фото лота");
////                model.addAttribute("lotForm", form);
////                Lot lot = lotRepo.findLotById(id);
////                model.addAttribute("lot", lot);
////                return "editLot";
////            }
////            form.setPhoto(photo); // сохранить фото в объекте RegistrationForm
////        } catch (Exception e) {
////            return "redirect:/lots/{id}/edit?error";
////        }
//        Lot lotToUpdate = form.toLot();
//        try {
//            if(photo.getSize() > 0) {
//                form.setPhoto(photo);
//            }else{
//                Lot lot = lotRepo.findLotById(id);
//                lotToUpdate.setPhotoOfLot(lot.getPhotoOfLot());
//            }
//        } catch (Exception e) {
//            return "redirect:/lots/{id}/edit?error";
//        }
//        lotToUpdate.setId(id);
//
//        lotToUpdate.setUser(user);
//        lotToUpdate.setCity(user.getCity());
//        lotToUpdate.setStreet(user.getStreet());
//
//        lotRepo.save(lotToUpdate);
//
//        sessionStatus.setComplete();
//
//        return "redirect:/lots";
//    }

//    @GetMapping("/{id}/edit")
//    public String editLot(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
//        Lot lot = lotRepo.findLotById(id);
//        if(!lot.getUser().getId().equals(user.getId())){
//            return "redirect:/";
//        }
//        model.addAttribute("lot", lot);
//        model.addAttribute("lotForm", getLotFormWithLot(lot));
//        return "editLot";
//    }

}
