package com.web.auction.controllers;

import com.web.auction.models.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Collection;

@Controller
@RequestMapping("/account")
public class PersonalAccountController {
    @GetMapping
    public String myAccount (Authentication authentication,@AuthenticationPrincipal User user,Model model){

        if (authentication != null) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/account/admin";
            }else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_MODERATOR"))) {
                return "redirect:/account/moderator";
            }
        }

        if (user.getPhoto() != null) {
            byte[] photoBytes = user.getPhoto();
            String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
            model.addAttribute("photoBase64", photoBase64);
        }

        model.addAttribute("user", user);

        return "personalAccount";
    }

    @PostMapping("/uploadNewPhoto")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal User user) throws IOException {
        // Допустим, у вас есть сервис photoService, который сохраняет фото

        if(file.getSize()<=0){
            return "redirect:/account?error";
        }

        try {
            user.setPhoto(file.getBytes()); // Реализация сохранения фото в соответствующее место
        } catch (IOException e) {
            return "redirect:/account?error";
        }

        // Вернуть пользователя на страницу профиля после загрузки
        return "redirect:/account";
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard(@AuthenticationPrincipal User user,Model model) {
        // Логика для административной панели
        if (user.getPhoto() != null) {
            byte[] photoBytes = user.getPhoto();
            String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
            model.addAttribute("photoBase64", photoBase64);
        }

        model.addAttribute("user", user);
        return "adminDashboard";
    }

    @GetMapping("/moderator")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorDashboard(@AuthenticationPrincipal User user,Model model) {
        // Логика для панели модератора
        if (user.getPhoto() != null) {
            byte[] photoBytes = user.getPhoto();
            String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
            model.addAttribute("photoBase64", photoBase64);
        }

        model.addAttribute("user", user);
        return "moderatorDashboard";
    }
}
